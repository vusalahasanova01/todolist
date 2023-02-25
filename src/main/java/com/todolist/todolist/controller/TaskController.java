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

  @GetMapping("/getTasks/userid/{userid}")
  public List<Task> getTasksByUserId(@PathVariable Long userid) {
    return taskService.getTasksByUserId(userid);
  }

  @GetMapping("/getArchivedTasks/userid/{userid}")
  public List<Task> getArchivedTasks(@PathVariable Long userid) {
    return taskService.getArchiveTasks(userid);
  }

  @PutMapping("archive/id{id}")
  public  void archiveTask(@PathVariable Long id){
    taskService.archiveTask(id);
  }

  @PutMapping("unarchive/id{id}")
  public  void unarchiveTask(@PathVariable Long id){
    taskService.unArchiveTask(id);
  }

  @PostMapping("/addTask/userid/{userId}")
  public ResponseEntity<Task> createTask(@PathVariable Long userId, @RequestBody Task task) {
    Task createdTask = taskService.createTaskById(userId, task);
    return ResponseEntity.ok(createdTask);
  }

  @PutMapping("id/{taskId}")
  public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
    Task updatedTask = taskService.updateTask(taskId, task);
    return ResponseEntity.ok(updatedTask);
  }

  @DeleteMapping("/delete/id/{taskId}")
  public void deleteTask(@PathVariable Long taskId){
    taskService.deleteTask(taskId);
  }
}
