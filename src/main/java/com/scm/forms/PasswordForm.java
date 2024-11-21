package com.scm.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordForm {

    private String currentPassword;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Min 8 characters required")
    private String newPassword;

}
