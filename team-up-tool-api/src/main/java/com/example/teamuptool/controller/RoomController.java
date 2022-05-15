package com.example.teamuptool.controller;


import com.example.teamuptool.service.RoomService;
import com.example.teamuptool.service.security.AdminAuthorize;
import com.example.teamuptool.service.security.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    private final static Logger LOGGER = Logger.getLogger(RoomController.class.getName());

    @GetMapping("/getRooms")
    public ResponseEntity<?> getRooms(){

        LOGGER.info("Begin request for getting collection of rooms");

        List<String> rooms = roomService.getAllRooms();
        LOGGER.info("Collection of names of rooms returned");

        return new ResponseEntity<>(rooms,HttpStatus.OK);

    }

}
