package com.example.teamuptool.repository;

import com.example.teamuptool.model.Meeting;
import com.example.teamuptool.model.ProjectManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MeetingRepository extends JpaRepository<Meeting, Integer > {

    List<Meeting> findAll();
}
