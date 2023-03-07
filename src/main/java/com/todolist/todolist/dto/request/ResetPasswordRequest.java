package com.todolist.todolist.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todolist.todolist.validation.constraints.Email;
import com.todolist.todolist.validation.constraints.ValidPassword;
import lombok.Data;

import java.util.Objects;

@Data
public class ResetPasswordRequest {

    @Email
    private String email;

    @ValidPassword
    private String password;

    @ValidPassword
    private String confirmPassword;

    @JsonIgnore
    public boolean isPasswordMatched() {
        return Objects.equals(password, confirmPassword);
    }

}
