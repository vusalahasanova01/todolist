package com.todolist.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TaskStatus {

    ARCHIVED(1),
    ACTIVE(2),
    UNSUPPORTED(-1);


    @Getter
    private final int id;

}
