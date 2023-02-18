package com.todolist.todolist.service;

import com.todolist.todolist.dao.entity.Task;
import com.todolist.todolist.dao.entity.User;
import com.todolist.todolist.dao.repository.TaskRepository;
import com.todolist.todolist.dao.repository.UserRepository;
import com.todolist.todolist.exception.UserNotFoundException;
import com.todolist.todolist.model.enums.TaskStatus;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public List<Task> getTasksByUserId(Long id) {
        //List<Task> tasks;
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(this::exUserNotFound);
        return user.getTasks();
    }


    public List<Task> getArchiveTasks(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(this::exUserNotFound);
        return user.getTasks().stream()
                .filter(this::isTaskStatusArchived)
                .collect(Collectors.toList());

    }

    private boolean isTaskStatusArchived(Task task) {
        if (Objects.isNull(task)) return false;
        return TaskStatus.ARCHIVE.equals(task.getTaskStatus());
    }

    private UserNotFoundException exUserNotFound() {
        return new UserNotFoundException("user not found");
    }

}
