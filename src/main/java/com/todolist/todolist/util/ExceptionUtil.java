package com.todolist.todolist.util;

import com.todolist.todolist.exception.TaskNotFoundException;
import com.todolist.todolist.exception.UserNotFoundException;

public class ExceptionUtil {
    public static TaskNotFoundException exTaskNotFound() {
        return new TaskNotFoundException("user not found");
    }
    public static UserNotFoundException exUserNotFound() {
        return new UserNotFoundException("user not found");
    }
    public static UnsupportedOperationException exUnsupported(){
        return new UnsupportedOperationException("task not exist");
    }
}
