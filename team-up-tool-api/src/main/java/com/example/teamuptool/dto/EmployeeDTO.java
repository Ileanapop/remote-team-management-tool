package com.example.teamuptool.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor

@Setter
@Getter
public class EmployeeDTO {

    @NotNull(message = "email must not be null")
    @Size(min = 1, max = 255, message = "Email '${validatedValue}' must be between {min} and {max} characters long")
    private String email;

    @NotNull(message = "pmanager must not be null")
    @Size(min = 1, max = 255, message = "Manager name '${validatedValue}' must be between {min} and {max} characters long")
    private String manager;

    @NotNull(message = "technology must not be null")
    @Size(min = 1, max = 255, message = "Technology '${validatedValue}' must be between {min} and {max} characters long")
    private String technology;

}
