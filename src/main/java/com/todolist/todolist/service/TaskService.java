package com.todolist.todolist.service;

import com.todolist.todolist.dao.entity.Task;
import com.todolist.todolist.dao.entity.User;
import com.todolist.todolist.dao.repository.TaskRepository;
import com.todolist.todolist.dao.repository.UserRepository;
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
}
