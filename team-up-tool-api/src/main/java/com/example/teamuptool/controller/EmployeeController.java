package com.example.teamuptool.controller;


import com.example.teamuptool.dto.AdministratorDTO;
import com.example.teamuptool.dto.AvailabilityDTO;
import com.example.teamuptool.dto.ListTasksDTO;
import com.example.teamuptool.dto.TaskDTO;
import com.example.teamuptool.service.AdministratorService;
import com.example.teamuptool.service.EmployeeService;
import com.example.teamuptool.service.UserService;
import com.example.teamuptool.service.security.AdminAuthorize;
import com.example.teamuptool.service.security.JWTUtil;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    private final static Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());

    @GetMapping("/searchUser")
    public ResponseEntity<?> getAvailabilityOfEmployee(@Param("email") String email, @Param("startTime")String startTime, @Param("endTime")String endTime) throws ParseException {

        LOGGER.info("Verify id the employee is Available in that time periods");

        ISO8601DateFormat df = new ISO8601DateFormat();
        Date startDate = df.parse(startTime);
        Date endDate = df.parse(endTime);
        //Date s = new Date(startTime);
       // Date e = new Date(endTime);
       // Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(startTime);
        //Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse(endTime);
        System.out.println(startTime);
        System.out.println(endTime);
        AvailabilityDTO availabilityDTO = userService.getUserByEmailAndAvailability(email,startDate,endDate);
        if(availabilityDTO == null){
            LOGGER.info("Service returned null");
            return ResponseEntity.badRequest().body("Employee not found");
        }
        else
            return new ResponseEntity<>(availabilityDTO, HttpStatus.OK);

    }

    @GetMapping("/getTasks")
    public ResponseEntity<?> getEmployeeTasks(@Param("email") String email){

        LOGGER.info("Get tasks of employee ");

        List<TaskDTO> tasks = employeeService.getEmployeeTasks(email);

        if(tasks == null){
            return ResponseEntity.badRequest().body("Employee not found");
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @DeleteMapping("/doTasks")
    public ResponseEntity<?> doTasks(@Valid @RequestBody ListTasksDTO listTasksDTO, HttpServletRequest request){

        LOGGER.info("Delete tasks of employee ");

        boolean result = employeeService.completeTasks(listTasksDTO);

        if(!result){
            return ResponseEntity.badRequest().body("Tasks cannot be marked as completed");
        }
        return new ResponseEntity<>("Tasks marked as completed", HttpStatus.OK);
    }

}
