package com.example.teamuptool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

@Setter
@Getter
public class ListTasksDTO {

    @NotEmpty(message = "Task list cannot be empty.")
    private List<Integer> tasksIds;

    @NotNull(message = "Employee must not be null")
    @Size(min = 1, max = 255, message = "Employee name '${validatedValue}' must be between {min} and {max} characters long")
    private String employee;

}
