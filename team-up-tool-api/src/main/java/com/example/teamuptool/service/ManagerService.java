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
import java.util.logging.Logger;

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

    private final static Logger LOGGER = Logger.getLogger(ManagerService.class.getName());

    public ManagerService(){

    }

    /**
     * Method for getting the existing role types for employees
     * @return list of role types
     */
    public List<String> getAllRoleTypes(){
        List<String> namesNotIn = new ArrayList<>();

        namesNotIn.add("Team Lead");
        namesNotIn.add("Active Customer");

        LOGGER.info("Initialize list of role types");
        List<RoleType> roleTypes = roleTypesRepository.getAllByNameIsNotIn(namesNotIn);

        List<String> roles = new ArrayList<>();

        LOGGER.info("Construct list of types");
        for(RoleType roleType: roleTypes){
            roles.add(roleType.getName());
        }
        LOGGER.info("Return list of role types");
        return  roles;
    }

    /**
     * Method for getting the current project of the manager
     * @param email of the manager
     * @return name of the current project
     */
    public String getProject(String email){

        LOGGER.info("Check if emanager exists");
        Optional<ProjectManager> projectManager = projectManagerRepository.findByAccount_Email(email);

        if(projectManager.isEmpty()){
            return "User not found";
        }

        LOGGER.info("Get project role");
        if(projectManager.get().getProjectRole()!=null){
            return projectManager.get().getProjectRole().getCompanyProject().getName();
        }
        return "No project assigned";

    }


    /**
     * Method for verifying if an employee is available for getting assigned a role
     * @param emailEmployee of the employee
     * @param emailManager of the manager
     * @return availability status of the searched employee
     */
    public AvailabilityDTO searchEmployee(String emailEmployee, String emailManager){
        LOGGER.info("Check email of employee");
       Optional<Employee> employee = employeeRepository.findByAccount_Email(emailEmployee);
       if(employee.isEmpty()){
           return new AvailabilityDTO("User not found", "User not found");
       }

        LOGGER.info("Check manager");
        Optional<ProjectManager> projectManager = projectManagerRepository.findByAccount_Email(emailManager);

       if(projectManager.isEmpty()){
           return new AvailabilityDTO("Manager not found", "Manager not found");
       }

        LOGGER.info("Verify if the employee has the same manager");

       if(employee.get().getProjectManager()!=null) {
           if (employee.get().getProjectManager().getId().equals(projectManager.get().getId())) {
               return new AvailabilityDTO(emailEmployee, "Available");
           }
           return new AvailabilityDTO("Employee not in the team", "Employee not in the team");
       }

        return new AvailabilityDTO("Employee not assigned to a manager", "Employee not assigned to a manager");
    }


    /**
     * Method for assigning a new role to an employee
     * @param roleDTO contains new role details including role type, description and project
     * @return true is the new role was assigned successfully
     */
    public boolean assignRole(RoleDTO roleDTO){

        LOGGER.info("Check if employee exists");
        Optional<Employee> employee = employeeRepository.findByAccount_Email(roleDTO.getEmployee());

        if(employee.isEmpty())
            return false;
        Optional<RoleType> roleType = roleTypesRepository.findByName(roleDTO.getRoleType());
        if(roleType.isEmpty())
            return false;

        Optional<Project> project = projectRepository.findByName(roleDTO.getProject());

        if(project.isEmpty())
            return false;

        LOGGER.info("Update project role of employee");
        ProjectRole projectRole = new ProjectRole();
        projectRole.setCompanyProject(project.get());
        projectRole.setDescription(roleDTO.getDescription());
        projectRole.setRoleType(roleType.get());
        employee.get().setProjectRole(projectRole);

        roleRepository.save(projectRole);
        employeeRepository.save(employee.get());
        return true;
    }


    /**
     * Method for assigning a new task to an employee
     * @param taskDTO contains data describing the new task including the employee, the manager who assigned the task,
     *               deadline and description
     * @return true if the new task was created and assigned successfully
     * @throws ParseException if the deadline date could not be parsed accordingly
     */
    public boolean assignTask(TaskDTO taskDTO) throws ParseException {

        LOGGER.info("Check if employee exists");
        Optional<Employee> employee = employeeRepository.findByAccount_Email(taskDTO.getEmployee());
        if(employee.isEmpty()){
            return false;
        }

        LOGGER.info("Check if manager exists");
        Optional<ProjectManager> projectManager = projectManagerRepository.findByAccount_Email(taskDTO.getManager());
        if(projectManager.isEmpty()){
            return false;
        }

        LOGGER.info("Convert data to correct format");
        ISO8601DateFormat df = new ISO8601DateFormat();
        Date deadline = df.parse(taskDTO.getDeadline());

        LOGGER.info("Create new task");
        Task newTask = new Task();
        newTask.setDescription(taskDTO.getDescription());
        newTask.setDeadline(deadline);
        newTask.setEmployee(employee.get());
        newTask.setProjectManager(projectManager.get());

        LOGGER.info("Save task");
        taskRepository.save(newTask);

        return true;
    }
}
