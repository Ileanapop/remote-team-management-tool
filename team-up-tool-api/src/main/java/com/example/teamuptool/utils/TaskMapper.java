package com.example.teamuptool.utils;

import com.example.teamuptool.dto.TaskDTO;
import com.example.teamuptool.dto.UserDTO;
import com.example.teamuptool.model.RegularUser;
import com.example.teamuptool.model.Task;

public class TaskMapper {

    public TaskDTO convertToDto(Task task){

        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setEmployee(task.getEmployee().getAccount().getEmail());
        taskDTO.setDeadline(task.getDeadline().toString());
        taskDTO.setManager(task.getProjectManager().getAccount().getEmail());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setId(task.getId());
        return taskDTO;
    }

}
