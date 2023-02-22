package com.todolist.todolist.dto.request;

import com.todolist.todolist.validation.constraints.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import java.util.Objects;

@Data
public class RegisterRequest {

    @Email
    private String email;
    private String fullName;

    @ValidPassword
    private String password;

    @ValidPassword
    private String confirmPassword;

    public boolean isPasswordsMatched() {
        return Objects.nonNull(password) && password.equals(confirmPassword);
    }

}