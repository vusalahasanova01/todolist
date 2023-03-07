package com.todolist.todolist.service;

import com.todolist.todolist.dao.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User getUserById(Long id);

    User getByUsername(String username);

    void save(User user);

    User getByVerificationCode(String verificationCode);

    void delete(User user);

    void deleteByUsername(String username);

    User getByResetPasswordToken(String token);

    void updateResetPasswordToken(String token, String email);

    void updateForgottenPassword(String email , String newPassword);

    void enableResetPassword(User user);


}
