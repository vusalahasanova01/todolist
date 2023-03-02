package com.todolist.todolist.exception;

public class PasswordsNotMatchedException extends RuntimeException {

    public PasswordsNotMatchedException(String errorMessage) {
        super(errorMessage);
    }

}
