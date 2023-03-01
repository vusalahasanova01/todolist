package com.todolist.todolist.controller;

import com.todolist.todolist.dao.entity.User;
import com.todolist.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("/delete/username/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
    }

}
