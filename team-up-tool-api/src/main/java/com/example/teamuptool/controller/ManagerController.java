package com.example.teamuptool.controller;


import com.example.teamuptool.dto.*;
import com.example.teamuptool.service.ManagerService;
import com.example.teamuptool.service.security.AuthResponse;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final static Logger LOGGER = Logger.getLogger(ManagerController.class.getName());

    @Autowired
    private ManagerService managerService;

    @GetMapping("/getRoles")
    public ResponseEntity<?> getActiveProjects(){

        LOGGER.info("Begin request for getting collection of role types");

        List<String> roles = managerService.getAllRoleTypes();
        LOGGER.info("Collection of roles returned");

        return new ResponseEntity<>(roles, HttpStatus.OK);

    }

    @GetMapping("/searchUser")
    public ResponseEntity<?> getAvailabilityOfEmployee(@Param("emailEmployee") String emailEmployee, @Param("emailManager")String emailManager) throws ParseException {

        LOGGER.info("Get availability of employee");

        AvailabilityDTO availabilityDTO = managerService.searchEmployee(emailEmployee,emailManager);

        return new ResponseEntity<>(availabilityDTO, HttpStatus.OK);

    }

    @GetMapping("/getProject")
    public ResponseEntity<?> getProject(@Param("email") String email) {

        LOGGER.info("Get project of manager");
        return new ResponseEntity<>(managerService.getProject(email), HttpStatus.OK);

    }

    @PutMapping("/assignRole")
    public ResponseEntity<?> assignRole(@Valid @RequestBody RoleDTO roleDTO, HttpServletRequest request){

        LOGGER.info("Begin request for assigning role to employee");

        boolean updateResult =managerService.assignRole(roleDTO);
        if(updateResult){
            LOGGER.info("Employee assigned role");
            return new ResponseEntity<>("New role assigned successfully",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Error at assigning role",HttpStatus.OK);

    }

    @PostMapping("/giveTask")
    public ResponseEntity<?> createUser(@Valid @RequestBody TaskDTO taskDTO, HttpServletRequest request) throws ParseException {

        LOGGER.info("Give Task request started");

        boolean result = managerService.assignTask(taskDTO);

        if(!result){
            return ResponseEntity.badRequest().body("Incorrect task details");
        }

        LOGGER.info("Task assigned and created successfully");
        return new ResponseEntity<>("New task created", HttpStatus.OK);
    }

}
