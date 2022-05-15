package com.example.teamuptool.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


public class SecurityContext {

    private SecurityStrategy securityStrategy;

    public void setSecurityStrategy(SecurityStrategy securityStrategy){
        this.securityStrategy = securityStrategy;
    }

    public ResponseEntity<?> secureRequest(HttpServletRequest request){

        AuthResponse response = securityStrategy.authorizeUser(request);
        System.out.println(response);
        if(response == AuthResponse.UNAUTHENTICATED){
            return new ResponseEntity<String>("UNAUTHENTICATED", HttpStatus.UNAUTHORIZED);
        }

        if(response == AuthResponse.UNAUTHORIZED){
            return new ResponseEntity<String>("FORBIDDEN", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<String>("AUTHORIZED", HttpStatus.OK);
    }

}
