package com.example.teamuptool.dto;


import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor

@Builder
@Setter
@Getter
public class ProjectDTO {

    @NotNull(message = "Manager must not be null")
    @Size(min = 1, max = 255, message = "Manager name '${validatedValue}' must be between {min} and {max} characters long")
    private String manager;

    @NotNull(message = "Project name must not be null")
    @Size(min = 1, max = 255, message = "Project name '${validatedValue}' must be between {min} and {max} characters long")
    private String name;

    @NotNull(message = "Status name must not be null")
    @Size(min = 1, max = 255, message = "Status name '${validatedValue}' must be between {min} and {max} characters long")
    private String status;

}
