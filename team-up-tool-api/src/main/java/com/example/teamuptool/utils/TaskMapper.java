package com.example.teamuptool.utils;

import com.example.teamuptool.dto.TaskDTO;
import com.example.teamuptool.dto.UserDTO;
import com.example.teamuptool.model.RegularUser;
import com.example.teamuptool.model.Task;

public class TaskMapper {

    public TaskDTO convertToDto(Task task){
        return TaskDTO.builder()
                .employee(task.getEmployee().getAccount().getEmail())
                .deadline(task.getDeadline().toString())
                .manager(task.getProjectManager().getAccount().getEmail())
                .description(task.getDescription())
                .id(task.getId()).build();

    }
}
