package com.example.teamuptool.service;


import com.example.teamuptool.dto.AvailabilityDTO;
import com.example.teamuptool.dto.RoleDTO;
import com.example.teamuptool.dto.TaskDTO;
import com.example.teamuptool.model.*;
import com.example.teamuptool.repository.*;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ManagerService {

    @Autowired
    private RoleTypesRepository roleTypesRepository;

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TaskRepository taskRepository;


    public ManagerService(){

    }

    public List<String> getAllRoleTypes(){
        List<String> namesNotIn = new ArrayList<>();

        namesNotIn.add("Team Lead");
        namesNotIn.add("Active Customer");
        List<RoleType> roleTypes = roleTypesRepository.getAllByNameIsNotIn(namesNotIn);

        List<String> roles = new ArrayList<>();

        for(RoleType roleType: roleTypes){
            roles.add(roleType.getName());
        }
        return  roles;
    }

    public String getProject(String email){

        Optional<ProjectManager> projectManager = projectManagerRepository.findByAccount_Email(email);

        if(projectManager.isEmpty()){
            return "User not found";
        }

        if(projectManager.get().getProjectRole()!=null){
            return projectManager.get().getProjectRole().getCompanyProject().getName();
        }
        return "No project assigned";

    }


    public AvailabilityDTO searchEmployee(String emailEmployee, String emailManager){

       Optional<Employee> employee = employeeRepository.findByAccount_Email(emailEmployee);
       if(employee.isEmpty()){
           return new AvailabilityDTO("User not found", "User not found");
       }
        Optional<ProjectManager> projectManager = projectManagerRepository.findByAccount_Email(emailManager);

       if(projectManager.isEmpty()){
           return new AvailabilityDTO("Manager not found", "Manager not found");
       }

       if(employee.get().getProjectManager()!=null) {
           if (employee.get().getProjectManager().getId().equals(projectManager.get().getId())) {
               return new AvailabilityDTO(emailEmployee, "Available");
           }
           return new AvailabilityDTO("Employee not in the team", "Employee not in the team");
       }

        return new AvailabilityDTO("Employee not assigned to a manager", "Employee not assigned to a manager");
    }

    public boolean assignRole(RoleDTO roleDTO){

        Optional<Employee> employee = employeeRepository.findByAccount_Email(roleDTO.getEmployee());

        if(employee.isEmpty())
            return false;
        Optional<RoleType> roleType = roleTypesRepository.findByName(roleDTO.getRoleType());
        if(roleType.isEmpty())
            return false;

        Optional<Project> project = projectRepository.findByName(roleDTO.getProject());

        if(project.isEmpty())
            return false;

        ProjectRole projectRole = new ProjectRole();
        projectRole.setCompanyProject(project.get());
        projectRole.setDescription(roleDTO.getDescription());
        projectRole.setRoleType(roleType.get());
        employee.get().setProjectRole(projectRole);

        roleRepository.save(projectRole);
        employeeRepository.save(employee.get());
        return true;
    }


    public boolean assignTask(TaskDTO taskDTO) throws ParseException {

        Optional<Employee> employee = employeeRepository.findByAccount_Email(taskDTO.getEmployee());
        if(employee.isEmpty()){
            return false;
        }

        Optional<ProjectManager> projectManager = projectManagerRepository.findByAccount_Email(taskDTO.getManager());
        if(projectManager.isEmpty()){
            return false;
        }

        ISO8601DateFormat df = new ISO8601DateFormat();
        Date deadline = df.parse(taskDTO.getDeadline());

        Task newTask = new Task();
        newTask.setDescription(taskDTO.getDescription());
        newTask.setDeadline(deadline);
        newTask.setEmployee(employee.get());
        newTask.setProjectManager(projectManager.get());

        taskRepository.save(newTask);

        return true;
    }
}
