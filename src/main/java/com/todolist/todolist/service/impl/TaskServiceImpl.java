package com.todolist.todolist.service.impl;

import com.todolist.todolist.dao.entity.Task;
import com.todolist.todolist.dao.entity.User;
import com.todolist.todolist.dao.repository.TaskRepository;
import com.todolist.todolist.dao.repository.UserRepository;
import com.todolist.todolist.model.TaskStatus;
import com.todolist.todolist.service.TaskService;
import com.todolist.todolist.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public List<Task> getTasksByUserId(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(ExceptionUtil::exUserNotFound);
        return user.getTasks();
    }

    @Override
    public Task createTaskById(Long userId, Task task) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(ExceptionUtil::exUserNotFound);
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Override
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
                .orElseThrow(ExceptionUtil::exTaskNotFound);
    }



    @Override
    public List<Task> getArchiveTasks(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(ExceptionUtil::exUserNotFound);
        return user.getTasks().stream()
                .filter(this::isTaskStatusArchived)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void archiveTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        Task task = optionalTask.orElseThrow(ExceptionUtil:: exTaskNotFound);
        if (task.getTaskStatus().equals(TaskStatus.ACTIVE)) {
            throw ExceptionUtil.exUnsupported();
        }
        task.setTaskStatus(TaskStatus.ACTIVE);
        taskRepository.save(task);
    }

    private boolean isTaskStatusArchived(Task task) {
        if (Objects.isNull(task)) return false;
        return TaskStatus.ARCHIVED.equals(task.getTaskStatus());
    }

}