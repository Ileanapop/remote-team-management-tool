package com.example.teamuptool.utils;

import com.example.teamuptool.dto.UserDTO;
import com.example.teamuptool.model.RegularUser;

public class UserMapper {


    public UserDTO convertToDto(RegularUser regularUser){

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(regularUser.getAccount().getEmail());
        userDTO.setName(regularUser.getName());
        userDTO.setPassword(regularUser.getAccount().getPassword());
        userDTO.setPhone(regularUser.getPhone());

        return userDTO;
    }

}
