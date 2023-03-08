package com.todolist.todolist.service;

import com.todolist.todolist.dao.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User getByUsername(String username);

    void save(User user);

    User getByVerificationCode(String verificationCode);

    User getByResetPasswordToken(String token);

    void updateResetPasswordToken(String token, String email);

    void updateForgottenPassword(String email , String newPassword);

    void enableResetPassword(User user);


}
