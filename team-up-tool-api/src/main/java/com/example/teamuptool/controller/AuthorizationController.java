package com.example.teamuptool.controller;


import com.example.teamuptool.service.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/authorize")
public class AuthorizationController {

    @Autowired
    private AdminAuthorize adminAuthorize;

    @GetMapping("/admin")
    public ResponseEntity<?> authorizeAdmin(HttpServletRequest request) {

        AuthResponse response = adminAuthorize.authorizeAdmin(request);
        if(response == AuthResponse.UNAUTHENTICATED){
            return new ResponseEntity<String>("UNAUTHENTICATED", HttpStatus.UNAUTHORIZED);
        }
        if(response == AuthResponse.UNAUTHORIZED){
            return new ResponseEntity<String>("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<String>("AUTHORIZED", HttpStatus.OK);
    }

    @GetMapping("/employee")
    public ResponseEntity<?> authorizeEmployee(HttpServletRequest request) {

        SecurityContext context = new SecurityContext();
        context.setSecurityStrategy(new EmployeeAuthorize());
        return context.secureRequest(request);

    }

    @GetMapping("/manager")
    public ResponseEntity<?> authorizeManager(HttpServletRequest request) {

        SecurityContext context = new SecurityContext();
        context.setSecurityStrategy(new ManagerAuthorize());
        return context.secureRequest(request);

    }

    @GetMapping("/customer")
    public ResponseEntity<?> authorizeCustomer(HttpServletRequest request) {

        SecurityContext context = new SecurityContext();
        context.setSecurityStrategy(new CustomerAuthorize());
        return context.secureRequest(request);

    }



}
