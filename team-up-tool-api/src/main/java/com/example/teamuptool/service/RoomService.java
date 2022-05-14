package com.example.teamuptool.service;


import com.example.teamuptool.model.Room;
import com.example.teamuptool.repository.EmployeeRepository;
import com.example.teamuptool.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    private final static Logger LOGGER = Logger.getLogger(RoomService.class.getName());

    public RoomService() {
    }

    public List<String> getAllRooms(){

        List<Room> rooms = roomRepository.findAll();

        List<String> roomNames = new ArrayList<>();

        for(Room room: rooms){
            roomNames.add(room.getName());
        }

        return roomNames;
    }
}
