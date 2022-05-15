package com.example.teamuptool.utils;

import com.example.teamuptool.dto.UserDTO;
import com.example.teamuptool.model.RegularUser;

public class UserMapper {


    public UserDTO convertToDto(RegularUser regularUser){

        return UserDTO.builder()
                .email(regularUser.getAccount().getEmail())
                .name((regularUser.getName()))
                .password(regularUser.getAccount().getPassword())
                .phone(regularUser.getPhone())
                .build();

    }
}
