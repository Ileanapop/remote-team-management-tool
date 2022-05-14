package com.example.teamuptool.service.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminAuthorize {

    @Autowired
    JWTUtil jwtUtil;

    public AuthResponse authorizeAdmin(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader != null && !authHeader.isEmpty() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            System.out.println(jwt);
            if (jwt.isEmpty()) {
                return AuthResponse.UNAUTHENTICATED;
            } else {

                try {
                    String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    System.out.println(username);
                    String type = jwtUtil.retrieveUserType(jwt);
                    if (type.equals("admin")) {
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