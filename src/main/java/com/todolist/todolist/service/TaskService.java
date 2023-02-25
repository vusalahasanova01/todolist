package com.todolist.todolist.service;

import com.todolist.todolist.dao.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> getTasksByUserId(Long id);

    Task createTaskById(Long userId, Task task);

    Task updateTask(Long id, Task newTask);

    void archiveTask(Long id);

    void unArchiveTask(Long id);

    List<Task> getArchiveTasks(Long id);

    void deleteTask(Long id);


}
