package com.todolist.todolist.service;

import com.todolist.todolist.dao.entity.User;

public interface UserService {
    User getUserById(Long id);
}
