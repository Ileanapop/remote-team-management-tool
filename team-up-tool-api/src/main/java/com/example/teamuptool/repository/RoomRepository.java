package com.example.teamuptool.repository;

import com.example.teamuptool.model.ProjectStatus;
import com.example.teamuptool.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public interface RoomRepository extends JpaRepository<Room, Integer > {

    List<Room> findAll();
    Optional<Room> findByName(String name);
}
