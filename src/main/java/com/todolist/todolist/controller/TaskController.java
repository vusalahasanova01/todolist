package com.todolist.todolist.controller;

import com.todolist.todolist.dao.entity.Task;
import com.todolist.todolist.dto.request.TaskCreation;
import com.todolist.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/getTasks/email/{email}")
    public List<Task> getTasksByEmail(@PathVariable String email) {
        return taskService.getTasksByEmail(email);
    }

    @GetMapping("/getArchivedTasks/email/{email}")
    public List<Task> getArchivedTasks(@PathVariable String email) {
        return taskService.getArchiveTasks(email);
    }

    @GetMapping("/getTaskById/id/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("archive/id/{id}")
    public void archiveTask(@PathVariable Long id) {
        taskService.archiveTask(id);
    }

    @PutMapping("unarchive/id/{id}")
    public void unarchiveTask(@PathVariable Long id) {
        taskService.unArchiveTask(id);
    }

    @PostMapping("/addTask/email/{email}")
    public ResponseEntity<Task> createTask(@PathVariable String email, @RequestBody TaskCreation taskCreation) {
        Task createdTask = taskService.createTaskByEmail(email, taskCreation);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("id/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody TaskCreation taskCreation) {
        Task updatedTask = taskService.updateTask(taskId, taskCreation);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/delete/id/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

}
