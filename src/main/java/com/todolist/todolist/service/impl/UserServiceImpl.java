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
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException, VerificationFailedException {
        User userById = userRepository.findById(Long.parseLong(userId)).orElse(null);

        if (Objects.isNull(userById)) {
            throw ExceptionUtil.exUserNotFound();
        }

        if (Boolean.FALSE.equals(userById.getEnabled())) {
            throw ExceptionUtil.verificationFailed();
        }

        return new org.springframework.security.core.userdetails.User(
                userById.getEmail(), userById.getPassword(), Collections.emptyList());
    }


    @Override
    @Transactional
    public void deleteByUsername(String username) {
        userRepository.deleteByEmail(username);
    }

}
