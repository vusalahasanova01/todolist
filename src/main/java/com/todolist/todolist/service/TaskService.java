package com.todolist.todolist.service;

import com.todolist.todolist.dao.entity.Task;
import com.todolist.todolist.dao.entity.User;
import com.todolist.todolist.dao.repository.TaskRepository;
import com.todolist.todolist.dao.repository.UserRepository;
import com.todolist.todolist.exception.TaskNotFoundException;
import com.todolist.todolist.exception.UserNotFoundException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
  private final UserRepository userRepository;
  private final TaskRepository taskRepository;

  public List<Task> getTasksByUserId(Long id) {
    //List<Task> tasks;
    Optional<User> optionalUser = userRepository.findById(id);
    User user = optionalUser.orElseThrow(() -> new UserNotFoundException("user not found"));
    return user.getTasks();
  }

  public Task createTaskById(Long userId, Task task) {
    Optional<User> userOptional = userRepository.findById(userId);
    User user = userOptional.orElseThrow(() -> new UserNotFoundException(String.format("User with %d not found", userId)));
    task.setUser(user);
    return taskRepository.save(task);
  }

  public Task updateTask(Long id, Task newTask) {
    return taskRepository.findById(id)
            .map(task -> {
              task.setTaskName(newTask.getTaskName());
              task.setTaskStatus(newTask.getTaskStatus());
              task.setDescription(newTask.getDescription());
              task.setPhoto(newTask.getPhoto());
              task.setTaskSortType(newTask.getTaskSortType());
              task.setTaskDeadlineDate(newTask.getTaskDeadlineDate());
              return taskRepository.save(task);
            })
            .orElseThrow(() -> new TaskNotFoundException(String.format("Task with %d not found", id)));
  }
}
