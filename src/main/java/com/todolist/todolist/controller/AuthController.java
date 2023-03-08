package com.todolist.todolist.controller;

import com.todolist.todolist.dto.request.RegisterRequest;
import com.todolist.todolist.dto.request.ResetPasswordRequest;
import com.todolist.todolist.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code) {
        return authService.verify(code);
    }

    @GetMapping("/verify/reset-password")
    public String verifyResetPassword(@RequestParam("token") String token) {
        return authService.verifyResetPassword(token);
    }

    @PutMapping("/process/reset-password")
    public void processResetPassword(@RequestParam("email") String email) {
        authService.processResetPassword(email);
    }

    @PutMapping("reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
    }


}
