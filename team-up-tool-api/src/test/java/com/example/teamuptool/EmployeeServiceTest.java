package com.example.teamuptool;

import com.example.teamuptool.dto.ListTasksDTO;
import com.example.teamuptool.dto.TaskDTO;
import com.example.teamuptool.model.Account;
import com.example.teamuptool.model.Employee;
import com.example.teamuptool.model.ProjectManager;
import com.example.teamuptool.model.Task;
import com.example.teamuptool.repository.*;
import com.example.teamuptool.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;


    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TaskRepository taskRepository;


    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getTasksOfNonExistingEmployeeShouldReturnNull(){

        Mockito.when(employeeRepository.findByAccount_Email(Mockito.anyString())).thenReturn(Optional.empty());

        List<TaskDTO> taskDTOS = employeeService.getEmployeeTasks("wrong_email");

        Assertions.assertNull(taskDTOS);
    }

    @Test
    public void getTasksOfEmployeeShouldReturnListTasks(){

       Employee employee = new Employee();
       List<Task> employeeTasks = new ArrayList<>();
       Account account = new Account();
       account.setEmail("email");
       employee.setAccount(account);

       ProjectManager projectManager = new ProjectManager();
       Account accountManager = new Account();
       accountManager.setEmail("manager");
       projectManager.setAccount(accountManager);

       Date deadline = new Date();
       String description = "";


       Task task1 = new Task();
       task1.setEmployee(employee);
       task1.setId(1);
       task1.setProjectManager(projectManager);
       task1.setDeadline(deadline);
        task1.setDescription(description);

        Task task2 = new Task();
        task2.setEmployee(employee);
        task2.setId(2);
        task2.setProjectManager(projectManager);
        task2.setDeadline(deadline);
        task2.setDescription(description);


        employeeTasks.add(task1);
        employeeTasks.add(task2);
        employee.setTasks(employeeTasks);

        Mockito.when(employeeRepository.findByAccount_Email(Mockito.anyString())).thenReturn(Optional.of(employee));

        List<TaskDTO> returnedTasks = employeeService.getEmployeeTasks("email");

        Assertions.assertEquals(employeeTasks.size(),returnedTasks.size());
        Assertions.assertEquals(1,returnedTasks.get(0).getId());
        Assertions.assertEquals(2,returnedTasks.get(1).getId());
        Assertions.assertEquals("email",returnedTasks.get(0).getEmployee());
        Assertions.assertEquals("email",returnedTasks.get(1).getEmployee());

    }

    @Test
    public void completeTasksOfNonExistingEmployeeShouldReturnFalse(){


        Mockito.when(employeeRepository.findByAccount_Email(Mockito.anyString())).thenReturn(Optional.empty());

        ListTasksDTO listTasksDTO = new ListTasksDTO();
        listTasksDTO.setEmployee("wrong_email");

        boolean result = employeeService.completeTasks(listTasksDTO);

        Assertions.assertFalse(result);

    }

    @Test
    public void completeTasksOfExistingEmployeeShouldReturnTrue(){

        Employee employee = new Employee();
        Mockito.when(employeeRepository.findByAccount_Email(Mockito.anyString())).thenReturn(Optional.of(employee));

        ListTasksDTO listTasksDTO = new ListTasksDTO();

        List<Integer> taskIds = new ArrayList<>();
        taskIds.add(1);
        taskIds.add(2);
        taskIds.add(3);
        taskIds.add(4);

        listTasksDTO.setEmployee("email");

        listTasksDTO.setTasksIds(taskIds);

        boolean result = employeeService.completeTasks(listTasksDTO);

        Assertions.assertTrue(result);

    }



}
