package com.todolist.todolist.service;

import com.todolist.todolist.dto.request.RegisterRequest;
import com.todolist.todolist.dto.request.ResetPasswordRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthService {

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void register(RegisterRequest request);

    boolean verify(String verificationCode);

    boolean verifyResetPassword(String token);

    void processResetPassword(String email);

    void resetPassword(ResetPasswordRequest request);
}
