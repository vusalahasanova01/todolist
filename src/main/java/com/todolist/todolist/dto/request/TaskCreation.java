package com.todolist.todolist.dto.request;

import com.todolist.todolist.model.TaskSortType;
import com.todolist.todolist.model.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCreation {
    private String taskName;
    private TaskSortType taskSortType;
    private TaskStatus taskStatus;
    private String description;
    private LocalDate taskDeadlineDate;
}