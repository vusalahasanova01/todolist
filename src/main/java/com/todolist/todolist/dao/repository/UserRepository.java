package com.todolist.todolist.dao.repository;

import com.todolist.todolist.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    User findByVerificationCode(String code);
    
    void deleteByEmail(String email);
}
