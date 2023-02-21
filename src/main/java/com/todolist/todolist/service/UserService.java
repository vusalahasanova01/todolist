package com.todolist.todolist.service;

import com.todolist.todolist.dao.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User getUserById(Long id);

    void save(User user);
}
