package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.validators.ValidFile;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MultipartConfig
public class UserForm {

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Min 3 characters required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 12, message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Min 8 characters required")
    private String password;

    private String about;

    private String pic;

    @ValidFile(message = "Invalid File")
    private MultipartFile profilePic;

    private String cloudinaryImagePublicId;
}
