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


    /**
     * Method for getting existing rooms when creating a new meeting
     * @return list of room names
     */
    public List<String> getAllRooms(){

        LOGGER.info("Get All existing rooms");
        List<Room> rooms = roomRepository.findAll();

        LOGGER.info("Construct list of room names");
        List<String> roomNames = new ArrayList<>();

        for(Room room: rooms){
            roomNames.add(room.getName());
        }

        return roomNames;
    }
}
