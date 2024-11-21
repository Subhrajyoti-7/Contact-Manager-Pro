package com.scm.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NameForm {

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Min 3 characters required")
    private String name;

}
