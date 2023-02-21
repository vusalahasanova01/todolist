package com.todolist.todolist.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.todolist.dao.entity.User;
import com.todolist.todolist.dao.repository.UserRepository;
import com.todolist.todolist.exception.PasswordsNotMatchedException;
import com.todolist.todolist.model.dto.request.RegisterRequest;
import com.todolist.todolist.service.AuthService;
import com.todolist.todolist.service.UserService;
import com.todolist.todolist.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRepository userRepository;

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
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
            throw new RuntimeException("Refresh token is mising");
        }
    }

    @Override
    public void register(RegisterRequest request) {
        User user = new User();

        if (request.isPasswordsMatched()){
            user.setPassword(request.getPassword());
        } else {
            throw new PasswordsNotMatchedException("passwords not mathced");
        }

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRegistrationDate(LocalDateTime.now());

        userRepository.save(user);
    }

}
