package com.todolist.todolist.service;

import com.todolist.todolist.dao.entity.Task;
import com.todolist.todolist.dto.request.TaskCreation;

import java.util.List;

public interface TaskService {
    List<Task> getTasksByEmail(String email);

    Task createTaskByEmail(String email, TaskCreation taskCreation);

    Task updateTask(Long id, TaskCreation taskCreation);

    void archiveTask(Long id);

    void unArchiveTask(Long id);

    List<Task> getArchiveTasks(String email);

    void deleteTask(Long id);


}
