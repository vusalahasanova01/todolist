package com.todolist.todolist.service;

import com.todolist.todolist.dao.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> getTasksByEmail(String email);

    Task createTaskByEmail(String email, Task task);

    Task updateTask(Long id, Task newTask);

    void archiveTask(Long id);

    void unArchiveTask(Long id);

    List<Task> getArchiveTasks(String email);

    void deleteTask(Long id);


}
