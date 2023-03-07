package com.todolist.todolist.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.todolist.config.properties.SecurityConstants;
import com.todolist.todolist.dao.entity.User;
import com.todolist.todolist.dto.request.RegisterRequest;
import com.todolist.todolist.dto.request.ResetPasswordRequest;
import com.todolist.todolist.exception.DuplicateUsernameException;
import com.todolist.todolist.exception.EmailProviderException;
import com.todolist.todolist.exception.PasswordsNotMatchedException;
import com.todolist.todolist.exception.UserNotFoundException;
import com.todolist.todolist.service.AuthService;
import com.todolist.todolist.service.UserService;
import com.todolist.todolist.util.EmailProvider;
import com.todolist.todolist.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailProvider emailProvider;

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(SecurityConstants.BEARER_PREFIX)) {
            try {
                String username = SecurityUtil.getSubjectFromBearerToken(bearerToken);
                UserDetails user = userService.loadUserByUsername(username);

                String requestUrl = request.getRequestURL().toString();
                String accessToken = SecurityUtil.getAccessToken(user, requestUrl);
                String newRefreshToken = SecurityUtil.getRefreshToken(user, requestUrl);

                final Map<String, String> tokenMap = SecurityUtil.getTokenMap(accessToken, newRefreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), tokenMap);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());

                final Map<String, String> error = Map.of("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @Override
    public void register(RegisterRequest request) {
        if (Objects.nonNull(userService.getByUsername(request.getEmail()))) {
            throw new DuplicateUsernameException("duplicate username");
        }

        User user = new User();

        if (request.isPasswordsMatched()) {
            String password = request.getPassword();
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            user.setPassword(encodedPassword);
        } else {
            throw new PasswordsNotMatchedException("passwords not matched");
        }

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRegistrationDate(LocalDateTime.now());

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        try {
            emailProvider.sendRegistrationEmail(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmailProviderException("error occurred in email sending process.");
        }

        userService.save(user);
    }

    public boolean verify(String verificationCode) {
        User user = userService.getByVerificationCode(verificationCode);
        if (Objects.isNull(user) || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userService.save(user);
            return true;
        }
    }

    @Override
    public boolean verifyResetPassword(String token) {
        User user = userService.getByResetPasswordToken(token);
        if (user == null) {
            return false;
        }
        userService.enableResetPassword(user);
        return true;
    }

    @Override
    public void processResetPassword(String email) {
        User user = userService.getByUsername(email);

        if (user == null) {
            throw new UserNotFoundException("user not found");
        }

        String token = RandomString.make(64);
        userService.updateResetPasswordToken(token, email);
        try {
            emailProvider.sendResetPasswordEmail(user);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        if (request == null || !request.isPasswordMatched()) {
            throw new PasswordsNotMatchedException("password not matched");
        }

        userService.updateForgottenPassword(request.getEmail(), request.getPassword());
    }

}
