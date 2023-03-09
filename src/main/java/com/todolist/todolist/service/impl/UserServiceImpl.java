package com.todolist.todolist.service.impl;

import com.todolist.todolist.dao.entity.User;
import com.todolist.todolist.dao.repository.UserRepository;
import com.todolist.todolist.exception.UserNotFoundException;
import com.todolist.todolist.exception.VerificationFailedException;
import com.todolist.todolist.service.UserService;
import com.todolist.todolist.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getByVerificationCode(String verificationCode) {
        return userRepository.findByVerificationCode(verificationCode);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, VerificationFailedException {
        User userByEmail = userRepository.findByEmail(username);

        if (Objects.isNull(userByEmail)) {
            throw ExceptionUtil.exUserNotFound();
        }

        if (Boolean.FALSE.equals(userByEmail.getEnabled())) {
            throw ExceptionUtil.verificationFailed();
        }

        return new org.springframework.security.core.userdetails.User(
                userByEmail.getEmail(), userByEmail.getPassword(), Collections.emptyList());
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any user with the email " + email);
        }
    }

    @Override
    public void updateForgottenPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("user not found");
        }

        if (!Boolean.TRUE.equals(user.getResetEnabled())) {
            throw new UnsupportedOperationException();
        }

        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        user.setResetEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void enableResetPassword(User user) {
        user.setResetPasswordToken(null);
        user.setResetEnabled(true);
        userRepository.save(user);
    }

}
