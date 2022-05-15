package com.example.teamuptool.service.security;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


public interface SecurityStrategy {

    public AuthResponse authorizeUser(HttpServletRequest request);

}
