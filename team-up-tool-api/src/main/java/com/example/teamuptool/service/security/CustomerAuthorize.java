package com.example.teamuptool.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


public class CustomerAuthorize implements SecurityStrategy{



    public AuthResponse authorizeUser(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader != null && !authHeader.isEmpty() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            System.out.println(jwt);
            if (jwt.isEmpty()) {
                return AuthResponse.UNAUTHENTICATED;
            } else {

                try {
                    JWT2 jwtUtil = new JWT2();
                    String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    System.out.println(username);
                    String type = jwtUtil.retrieveUserType(jwt);
                    if (type.equals("customer")) {
                        return AuthResponse.AUTHORIZED;
                    } else
                        return AuthResponse.UNAUTHORIZED;
                }
                catch (Exception exception){
                    return AuthResponse.UNAUTHENTICATED;
                }
            }
        }
        return AuthResponse.UNAUTHENTICATED;
    }

}
