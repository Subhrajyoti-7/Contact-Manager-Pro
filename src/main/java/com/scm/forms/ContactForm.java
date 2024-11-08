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
public class ContactForm {

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Min 3 characters required")
    private String name;

    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 12, message = "Invalid phone number")
    private String phone;

    private String address;

    @ValidFile(message = "Invalid File") // Using Custom validator
    // OR using custom validator (file size should be in bytes)
    // @Max(value = 2097152, message = "File size must be less than 2MB")
    private MultipartFile pic;

    private String picture;

    private String cloudinaryImagePublicId;

    private String facebook;

    private String instagram;

}
