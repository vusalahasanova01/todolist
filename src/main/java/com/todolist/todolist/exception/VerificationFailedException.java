package com.todolist.todolist.exception;

public class VerificationFailedException extends RuntimeException{

    public VerificationFailedException(String errorMessage) {
        super(errorMessage);
    }

}
