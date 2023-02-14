package com.todolist.todolist.dao.repository;

import com.todolist.todolist.dao.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
