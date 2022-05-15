package com.example.teamuptool.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor

@Builder
@Setter
@Getter
public class TaskDTO {


    @NotNull(message = "Deadline date cannot be null")
    private String deadline;

    @NotNull(message = "Description date cannot be null")
    @Size(min = 1, max = 255, message = "Description '${validatedValue}' must be between {min} and {max} characters long")
    private String description;

    @NotNull(message = "Manager who assigned the task cannot be null")
    @Size(min = 1, max = 255, message = "Manager '${validatedValue}' must be between {min} and {max} characters long")
    private String manager;

    @NotNull(message = "Employee cannot be null")
    @Size(min = 1, max = 255, message = "Employee '${validatedValue}' must be between {min} and {max} characters long")
    private String employee;

    private Integer id;

}
