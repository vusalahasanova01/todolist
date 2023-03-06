package com.todolist.todolist.service.impl;

import com.todolist.todolist.dao.entity.Task;
import com.todolist.todolist.dao.entity.User;
import com.todolist.todolist.dao.repository.TaskRepository;
import com.todolist.todolist.dao.repository.UserRepository;
import com.todolist.todolist.dto.request.TaskCreation;
import com.todolist.todolist.exception.UserNotFoundException;
import com.todolist.todolist.model.TaskStatus;
import com.todolist.todolist.service.TaskService;
import com.todolist.todolist.util.ExceptionUtil;
import com.todolist.todolist.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<Task> getTasksByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user.getTasks();
    }


    @Override
    public Task createTaskByEmail(String email, TaskCreation taskCreation) {
        User user = userRepository.findByEmail(email);
        Task task = Mapper.map(taskCreation, Task.class);
        task.setTaskStatus(taskCreation.getTaskStatus());
        task.setTaskSortType(taskCreation.getTaskSortType());
        task.setTaskCreateDate(LocalDate.now());
        if (user != null) {
            task.setUser(user);
        } else {
            throw new UserNotFoundException("user not found");
        }
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, TaskCreation taskCreation) {
        return taskRepository.findById(id)
                .map(task -> {
                    if(taskCreation.getTaskName() != null) task.setTaskName(taskCreation.getTaskName());
                    if(taskCreation.getTaskStatus() != null) task.setTaskStatus(taskCreation.getTaskStatus());
                    if(taskCreation.getDescription() != null) task.setDescription(taskCreation.getDescription());
                    if(taskCreation.getTaskSortType() != null) task.setTaskSortType(taskCreation.getTaskSortType());
                    if(taskCreation.getTaskDeadlineDate() != null) task.setTaskDeadlineDate(taskCreation.getTaskDeadlineDate());
                    return taskRepository.save(task);
                })
                .orElseThrow(ExceptionUtil::exTaskNotFound);
    }



    @Override
    public List<Task> getArchiveTasks(String email) {
        User user = userRepository.findByEmail(email);
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
        if ((TaskStatus.ARCHIVED).equals(task.getTaskStatus())) {
            throw ExceptionUtil.exUnsupported();
        }
        task.setTaskStatus(TaskStatus.ARCHIVED);
        taskRepository.save(task);
    }

    public void unArchiveTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        Task task = optionalTask.orElseThrow(ExceptionUtil:: exTaskNotFound);
        if ((TaskStatus.ACTIVE).equals(task.getTaskStatus())) {
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
