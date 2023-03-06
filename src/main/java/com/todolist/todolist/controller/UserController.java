package com.todolist.todolist.controller;

import com.todolist.todolist.dao.entity.User;
import com.todolist.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("/delete/username/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
    }

}
