package com.example.teamuptool;


import com.example.teamuptool.model.Room;
import com.example.teamuptool.repository.*;
import com.example.teamuptool.service.RoomService;
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
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;


    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllRoomsShouldReturnListRoomsNames(){

        List<Room> rooms = new ArrayList<>();
        Room room1 = new Room();
        room1.setName("Room1");

        Room room2 = new Room();
        room2.setName("Room2");

        rooms.add(room1);
        rooms.add(room2);

        Mockito.when(roomRepository.findAll()).thenReturn(rooms);

        List<String> returnedRoomsNames = roomService.getAllRooms();

        Assertions.assertEquals(rooms.size(),returnedRoomsNames.size());
        Assertions.assertEquals("Room1",returnedRoomsNames.get(0));
        Assertions.assertEquals("Room2",returnedRoomsNames.get(1));
    }
}
