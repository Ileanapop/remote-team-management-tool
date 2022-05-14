package com.example.teamuptool.controller;


import com.example.teamuptool.dto.*;
import com.example.teamuptool.service.AdministratorService;
import com.example.teamuptool.service.UserService;
import com.example.teamuptool.service.security.AdminAuthorize;
import com.example.teamuptool.service.security.AuthResponse;
import com.example.teamuptool.service.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;


    private final static Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @GetMapping("/login")
    public ResponseEntity<?> getUserByEmail(@Param("email") String email, @Param("password") String password,@Param("type") Integer type){

        LOGGER.info("Authentication started");
        try {
            UserLoginDTO userLoginDTO = new UserLoginDTO(email,password,type);
            UserDTO userDTO = userService.loginUser(userLoginDTO);
            if(userDTO==null)
                return ResponseEntity.badRequest().body("Invalid Login Credentials");

            String token=null;

            if(userLoginDTO.getType() == 1)
                token =  jwtUtil.generateToken(userLoginDTO.getEmail()+"-employee");
            if(userLoginDTO.getType() == 2)
                token =  jwtUtil.generateToken(userLoginDTO.getEmail()+"-manager");
            if(userLoginDTO.getType() == 3)
                token =  jwtUtil.generateToken(userLoginDTO.getEmail()+"-customer");

            LOGGER.info("Authentication finished successfully");
            System.out.println(token);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }catch (Exception exception){
            LOGGER.warning("Exception occurred during authentication");
            return ResponseEntity.badRequest().body("Invalid Login Credentials");
        }

    }

    @PostMapping("/createMeeting")
    public ResponseEntity<?> createUser(@Valid @RequestBody MeetingDTO meetingDTO, HttpServletRequest request) throws ParseException {

        LOGGER.info("Create meeting request started");

        boolean result = userService.createMeeting(meetingDTO);

        if(!result){
            return ResponseEntity.badRequest().body("Incorrect meeting details");
        }

        LOGGER.info("Meeting created successfully");
        return new ResponseEntity<>("Meeting created", HttpStatus.OK);
    }


    @GetMapping("/getMeetings")
    public ResponseEntity<?> getMeetings(@Param("email") String email){

        LOGGER.info("Request for getting the meetings started");

        List<ViewMeetingDTO> viewMeetingDTOs = userService.getMeetings(email);

        if(viewMeetingDTOs == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(viewMeetingDTOs, HttpStatus.OK);
    }


}
