package com.todolist.todolist.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TaskSortType {
    DELETED("Deleted", 1),
    OVERDUE("Overdue", 2),
    TODAY("Today", 3),
    DONE("Done", 4),
    UNSUPPORTED("Unsupported",-1);

    @Getter
    private final String name;

    @Getter
    private final int id;

}
