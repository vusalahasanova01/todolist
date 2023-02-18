package com.todolist.todolist.controller;

import com.todolist.todolist.dao.entity.Task;
import com.todolist.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;

  @GetMapping("/userid/{userid}")
  public List<Task> getTasksByUserId(@PathVariable Long userid) {
    return taskService.getTasksByUserId(userid);
  }

  @PostMapping("/addTask/{userId}")
  public ResponseEntity<Task> createTask(@PathVariable Long userId, @RequestBody Task task) {
    Task createdTask = taskService.createTaskById(userId, task);
    return ResponseEntity.ok(createdTask);
  }

  @PutMapping("/{taskId}")
  public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
    Task updatedTask = taskService.updateTask(taskId, task);
    return ResponseEntity.ok(updatedTask);
  }
}
