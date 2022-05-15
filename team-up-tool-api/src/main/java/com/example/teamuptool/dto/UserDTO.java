package com.example.teamuptool.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor

@Builder
@Setter
@Getter

public class UserDTO {

    @NotNull(message = "email must not be null")
    @Size(min = 1, max = 255, message = "Email '${validatedValue}' must be between {min} and {max} characters long")
    private String email;

    @NotNull(message = "password must not be null")
    @Size(min = 1, max = 255, message = "Password '${validatedValue}' must be between {min} and {max} characters long")
    private String password;


    @NotNull(message = "name must not be null")
    @Size(min = 1, max = 255, message = "Name '${validatedValue}' must be between {min} and {max} characters long")
    private String name;

    @NotNull(message = "phone must not be null")
    @Size(min = 1, max = 255, message = "Phone '${validatedValue}' must be between {min} and {max} characters long")
    private String phone;

    @NotNull(message = "user must have a type")
    private Integer type;


}
