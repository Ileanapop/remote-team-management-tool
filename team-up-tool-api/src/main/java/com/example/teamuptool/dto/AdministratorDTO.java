package com.example.teamuptool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AdministratorDTO {


    @NotNull(message = "email must not be null")
    @Size(min = 1, max = 255, message = "Email '${validatedValue}' must be between {min} and {max} characters long")
    private String email;

    @NotNull(message = "password must not be null")
    @Size(min = 1, max = 255, message = "Password '${validatedValue}' must be between {min} and {max} characters long")
    private String password;
}
