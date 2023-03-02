package com.todolist.todolist.util;

import com.todolist.todolist.exception.TaskNotFoundException;
import com.todolist.todolist.exception.UserNotFoundException;
import com.todolist.todolist.exception.VerificationFailedException;

public final class ExceptionUtil {

    public static TaskNotFoundException exTaskNotFound() {
        return new TaskNotFoundException("user not found");
    }

    public static UserNotFoundException exUserNotFound() {
        return new UserNotFoundException("user not found");
    }

    public static UnsupportedOperationException exUnsupported(){
        return new UnsupportedOperationException("task not exist");
    }

    public static VerificationFailedException verificationFailed(){
        return new VerificationFailedException("verification is failed");
    }
    private ExceptionUtil() {
    }

}
