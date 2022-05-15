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
import java.util.logging.Logger;

@Component
public class EmployeeService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private final static Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());

    public EmployeeService(){

    }

    /**
     * Method for getting tasks of employees
     * @param email of the employee
     * @return list of tasks along with deadlines and descriptions that the employee has to complete
     */
    public List<TaskDTO> getEmployeeTasks(String email){

        LOGGER.info("Check if employee exists");
        Optional<Employee> employee = employeeRepository.findByAccount_Email(email);

        if(employee.isEmpty())
            return null;

        LOGGER.info("Construct tasks list");
        List<TaskDTO> taskDTOS = new ArrayList<>();
        TaskMapper taskMapper = new TaskMapper();

        for(Task task: employee.get().getTasks()){

            taskDTOS.add(taskMapper.convertToDto(task));
        }

        return taskDTOS;
    }

    /**
     * Method for marking a list of tasks as done
     * @param listTasksDTO contains list of ids of the completed tasks and the email of the employee which completed them
     * @return true if the task were successfully marked as completed
     */
    public boolean completeTasks(ListTasksDTO listTasksDTO){

        LOGGER.info("Check if employee exists");
        Optional<Employee> employee = employeeRepository.findByAccount_Email(listTasksDTO.getEmployee());

        if(employee.isEmpty())
            return false;

        LOGGER.info("Delete tasks from tasks assigned to the employee");
        for(Integer task_id: listTasksDTO.getTasksIds()){
            taskRepository.deleteById(task_id);
        }

        return true;

    }


}
