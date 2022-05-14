package com.example.teamuptool.utils;

import com.example.teamuptool.dto.AdministratorDTO;
import com.example.teamuptool.model.Administrator;

public class AdministratorMapper {

    public Administrator convertFromDTO(AdministratorDTO administratorDTO){
        Administrator administrator = new Administrator();

        administrator.setEmail(administratorDTO.getEmail());
        administrator.setPassword(administratorDTO.getPassword());
        return administrator;
    }

    public AdministratorDTO convertToDTO(Administrator administrator){

        AdministratorDTO administratorDTO = new AdministratorDTO();

        administratorDTO.setEmail(administrator.getEmail());
        administratorDTO.setPassword(administrator.getPassword());
        return administratorDTO;
    }

}
