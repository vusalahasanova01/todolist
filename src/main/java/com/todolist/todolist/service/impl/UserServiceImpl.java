package com.todolist.todolist.service.impl;

import com.todolist.todolist.dao.entity.User;
import com.todolist.todolist.dao.repository.UserRepository;
import com.todolist.todolist.exception.VerificationFailedException;
import com.todolist.todolist.service.UserService;
import com.todolist.todolist.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(ExceptionUtil::exUserNotFound);
    }

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
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        userRepository.deleteByEmail(username);
    }

}
