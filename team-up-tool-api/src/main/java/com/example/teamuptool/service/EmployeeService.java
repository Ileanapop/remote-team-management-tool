package com.example.teamuptool.service;


import com.example.teamuptool.dto.ListTasksDTO;
import com.example.teamuptool.dto.TaskDTO;
import com.example.teamuptool.model.Employee;
import com.example.teamuptool.model.Task;
import com.example.teamuptool.repository.EmployeeRepository;
import com.example.teamuptool.repository.TaskRepository;
import com.example.teamuptool.utils.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public EmployeeService(){

    }

    public List<TaskDTO> getEmployeeTasks(String email){

        Optional<Employee> employee = employeeRepository.findByAccount_Email(email);

        if(employee.isEmpty())
            return null;

        List<TaskDTO> taskDTOS = new ArrayList<>();
        TaskMapper taskMapper = new TaskMapper();

        for(Task task: employee.get().getTasks()){

            taskDTOS.add(taskMapper.convertToDto(task));
        }

        return taskDTOS;
    }

    public boolean completeTasks(ListTasksDTO listTasksDTO){

        Optional<Employee> employee = employeeRepository.findByAccount_Email(listTasksDTO.getEmployee());

        if(employee.isEmpty())
            return false;

        for(Integer task_id: listTasksDTO.getTasksIds()){

            taskRepository.deleteById(task_id);
        }

        return true;

    }


}
