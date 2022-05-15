package com.example.teamuptool.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


public class ManagerAuthorize implements SecurityStrategy{


    public AuthResponse authorizeUser(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader != null && !authHeader.isEmpty() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            System.out.println(jwt);
            if (jwt.isEmpty()) {
                System.out.println("sdvdxfbdfb");
                return AuthResponse.UNAUTHENTICATED;
            } else {

                try {
                    JWT2 jwt2 = new JWT2();
                    String username = jwt2.validateTokenAndRetrieveSubject(jwt);
                    System.out.println(username);
                    String type = jwt2.retrieveUserType(jwt);
                    if (type.equals("manager")) {
                        System.out.println("ooooook");
                        return AuthResponse.AUTHORIZED;
                    } else
                        return AuthResponse.UNAUTHORIZED;
                }
                catch (Exception exception){
                    System.out.println("sdvdxfrhdfhfdtbdfb");
                    return AuthResponse.UNAUTHENTICATED;
                }
            }
        }
        return AuthResponse.UNAUTHENTICATED;
    }

}
