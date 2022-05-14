package com.example.teamuptool.repository;

import com.example.teamuptool.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TaskRepository extends JpaRepository<Task, Integer > {

     void deleteById(Integer id);

}
