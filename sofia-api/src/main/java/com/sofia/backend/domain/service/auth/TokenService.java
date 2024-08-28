package com.sofia.backend.domain.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sofia.backend.domain.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("{api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            return token;
        }catch(JWTCreationException e){
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch(JWTVerificationException e){
            return "";
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime
                .now()
                .plusHours(20)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public String generateRefreshToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String refreshToken = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateRefreshExpirationDate())
                    .sign(algorithm);

            return refreshToken;
        }catch(JWTCreationException e){
            throw new RuntimeException("Error while generating refresh token", e);
        }
    }

    private Instant generateRefreshExpirationDate(){
        return LocalDateTime
                .now()
                .plusDays(7)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public boolean isTokenValid(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);
            return true;
        }catch(JWTVerificationException e){
            return false;
        }
    }

}
