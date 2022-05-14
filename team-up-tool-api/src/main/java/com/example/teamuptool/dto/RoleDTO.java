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

public class RoleDTO {

    @NotNull(message = "Role assigned must not be null")
    private String roleType;

    @NotNull(message = "Description must not be null")
    private String description;

    @NotNull(message = "Project must not be null")
    private String project;

    @NotNull(message = "Employee must not be null")
    private String employee;

}
