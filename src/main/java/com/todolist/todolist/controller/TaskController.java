package com.todolist.todolist.controller;

import com.todolist.todolist.dao.entity.Task;
import com.todolist.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;

  @GetMapping("/userid{userid}")
  public List<Task> getTasksByUserId(@PathVariable Long userid) {
    return taskService.getTasksByUserId(userid);
  }

  @GetMapping("/userid{userid}")
  public List<Task> getArchiveTasks(@PathVariable Long userid){
    return taskService.getArchiveTasks(userid);
  }

}
