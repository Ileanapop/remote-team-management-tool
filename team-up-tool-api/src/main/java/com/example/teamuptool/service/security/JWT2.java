package com.example.teamuptool.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JWT2 {


    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username) throws IllegalArgumentException, JWTCreationException {

        String[] parts = username.split("-");
        String name = parts[0]; // 004
        String type = parts[1]; // 034556

        return JWT.create()
                .withSubject("UserAuth")
                .withIssuedAt(new Date())
                .withIssuer("TeamManagementTool")
                .withClaim("email", name)
                .withClaim("type", type)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("email").asString();
    }

    public String retrieveUserType(String token)throws JWTVerificationException {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("type").asString();
    }

}
