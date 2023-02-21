package com.todolist.todolist.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.todolist.service.AuthService;
import com.todolist.todolist.service.UserService;
import com.todolist.todolist.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

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

}
