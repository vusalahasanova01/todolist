package com.todolist.todolist.service;

import com.todolist.todolist.dao.entity.User;
import com.todolist.todolist.dao.repository.UserRepository;
import com.todolist.todolist.exception.UserNotFoundException;
import com.todolist.todolist.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(ExceptionUtil::exUserNotFound);
        return user;
    }
}
