package com.example.teamuptool.controller;


import com.example.teamuptool.dto.AdministratorDTO;
import com.example.teamuptool.dto.EmployeeDTO;
import com.example.teamuptool.dto.ProjectDTO;
import com.example.teamuptool.dto.UserDTO;
import com.example.teamuptool.service.AdministratorService;
import com.example.teamuptool.service.UserService;
import com.example.teamuptool.service.security.AdminAuthorize;
import com.example.teamuptool.service.security.AuthResponse;
import com.example.teamuptool.service.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AdminAuthorize adminAuthorize;


    private final static Logger LOGGER = Logger.getLogger(AdministratorController.class.getName());

    @GetMapping("/login")
    public ResponseEntity<?> getCustomerByUsername(@Param("email") String email, @Param("password") String password){

        LOGGER.info("Authentication started");
        try {
            AdministratorDTO administrator = administratorService.loginAdministrator(email,password);
            if(administrator==null)
                return ResponseEntity.badRequest().body("Invalid Login Credentials");

            String token = jwtUtil.generateToken(email+"-admin");

            LOGGER.info("Authentication finished successfully");
            System.out.println(token);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }catch (Exception exception){
            LOGGER.warning("Exception occurred during authentication");
            return ResponseEntity.badRequest().body("Invalid Login Credentials");
        }

    }

    @PostMapping("/createAccount")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {

        if(adminAuthorize.authorizeAdmin(request) == AuthResponse.UNAUTHENTICATED){
            return new ResponseEntity<String>("UNAUTHENTICATED", HttpStatus.UNAUTHORIZED);
        }

        LOGGER.info("Register operation started");
        UserDTO result = administratorService.saveUser(userDTO);
        System.out.println("sgbdfbedrnbdtngfdng");
        if(result == null){
            LOGGER.warning("Register cannot be performed");
            return ResponseEntity.badRequest().body("Insert new user cannot be performed");
        }
        LOGGER.info("Register finished successfully");
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/getTechnologies")
    public ResponseEntity<?> getTechnologies(HttpServletRequest request){

        LOGGER.info("Begin request for getting collection of available technologies");
        LOGGER.info("Authorize Admin");
        if(adminAuthorize.authorizeAdmin(request) == AuthResponse.UNAUTHENTICATED){
            return new ResponseEntity<String>("UNAUTHENTICATED", HttpStatus.UNAUTHORIZED);
        }
        LOGGER.info("Admin authorized");

        List<String> technologies = administratorService.getTechnologies();
        LOGGER.info("Collection of technologies returned");

        return new ResponseEntity<>(technologies,HttpStatus.OK);

    }

    @GetMapping("/getActiveProjects")
    public ResponseEntity<?> getActiveProjects(HttpServletRequest request){

        LOGGER.info("Begin request for getting collection of active projects");
        LOGGER.info("Authorize Admin");
        if(adminAuthorize.authorizeAdmin(request) == AuthResponse.UNAUTHENTICATED){
            return new ResponseEntity<String>("UNAUTHENTICATED", HttpStatus.UNAUTHORIZED);
        }
        LOGGER.info("Admin authorized");

        List<String> activeProjects = administratorService.getActiveProjects();
        LOGGER.info("Collection of active projects returned");

        return new ResponseEntity<>(activeProjects,HttpStatus.OK);

    }

    @GetMapping("/getManagers")
    public ResponseEntity<?> getManagers(HttpServletRequest request){

        LOGGER.info("Begin request for getting collection of managers");
        LOGGER.info("Authorize Admin");
        if(adminAuthorize.authorizeAdmin(request) == AuthResponse.UNAUTHENTICATED){
            return new ResponseEntity<String>("UNAUTHENTICATED", HttpStatus.UNAUTHORIZED);
        }
        LOGGER.info("Admin authorized");

        List<String> managers = administratorService.getManagers();
        LOGGER.info("Collection of managers returned");

        return new ResponseEntity<>(managers,HttpStatus.OK);

    }

    @GetMapping("/getEmployees")
    public ResponseEntity<?> getEmployees(HttpServletRequest request){

        LOGGER.info("Begin request for getting collection of employees");
        LOGGER.info("Authorize Admin");
        if(adminAuthorize.authorizeAdmin(request) == AuthResponse.UNAUTHENTICATED){
            return new ResponseEntity<String>("UNAUTHENTICATED", HttpStatus.UNAUTHORIZED);
        }
        LOGGER.info("Admin authorized");

        List<String> employees = administratorService.getEmployees();
        LOGGER.info("Collection of employees returned");

        return new ResponseEntity<>(employees,HttpStatus.OK);

    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<?> getEmployees(@Valid @RequestBody EmployeeDTO employeeDTO, HttpServletRequest request){

        LOGGER.info("Begin request for getting collection of employees");
        LOGGER.info("Authorize Admin");
        if(adminAuthorize.authorizeAdmin(request) == AuthResponse.UNAUTHENTICATED){
            return new ResponseEntity<String>("UNAUTHENTICATED", HttpStatus.UNAUTHORIZED);
        }
        LOGGER.info("Admin authorized");

        boolean updateResult = administratorService.updateEmployee(employeeDTO);
        if(updateResult){
            LOGGER.info("Employee Profile Updated");
            return new ResponseEntity<>("Complete Employee Profile",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Employee Profile cannot be updated",HttpStatus.OK);

    }

    @GetMapping("/getManagerData")
    public ResponseEntity<?> getManagerData(@Param("email") String email, HttpServletRequest request){

        LOGGER.info("Begin request for getting manager project data");
        LOGGER.info("Authorize Admin");
        if(adminAuthorize.authorizeAdmin(request) == AuthResponse.UNAUTHENTICATED){
            return new ResponseEntity<String>("UNAUTHENTICATED", HttpStatus.UNAUTHORIZED);
        }
        LOGGER.info("Admin authorized");

        ProjectDTO projectDTO = administratorService.getManagerProjectData(email);

        return new ResponseEntity<>(projectDTO,HttpStatus.OK);

    }

    @PutMapping("/updateManager")
    public ResponseEntity<?> updateManager(@Valid @RequestBody ProjectDTO projectDTO, HttpServletRequest request){

        LOGGER.info("Begin request for updating manager project data");
        LOGGER.info("Authorize Admin");
        if(adminAuthorize.authorizeAdmin(request) == AuthResponse.UNAUTHENTICATED){
            return new ResponseEntity<String>("UNAUTHENTICATED", HttpStatus.UNAUTHORIZED);
        }
        LOGGER.info("Admin authorized");

        boolean result = administratorService.updateManagerData(projectDTO);
        if(result)
            return new ResponseEntity<>(true,HttpStatus.OK);
        else
            return ResponseEntity.badRequest().body("Error: cannot perform update");

    }

}
