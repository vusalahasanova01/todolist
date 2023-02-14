package com.todolist.todolist.model.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TaskSortType {
    DELETED("Deleted"),
    OVERDUE("Overdue"),
    TODAY("Today"),
    DONE("Done");

    @Getter
    private final String name;

}
