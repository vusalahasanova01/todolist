package com.todolist.todolist.exception;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
