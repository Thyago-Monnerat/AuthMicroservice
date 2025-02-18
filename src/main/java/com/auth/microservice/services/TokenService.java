package com.auth.microservice.services;

import com.auth.microservice.dtos.UserLoginDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${security.key}")
    private String key;

    private final Algorithm algorithm = Algorithm.HMAC256(key);

    private final String issuer = "UserAuth";

    private Instant generateInstant() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String generateToken(UserLoginDTO userLoginDTO) {
        try {
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(userLoginDTO.username())
                    .withExpiresAt(generateInstant())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException(e.getMessage(), e);
        }
    }

    public String validateToken(String token){
        try {
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e){
            throw new JWTVerificationException(e.getMessage(), e);
        }
    }
}
