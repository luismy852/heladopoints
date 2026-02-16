package com.luisdev.heladopoints.infra.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.luisdev.heladopoints.exception.TokenCreationException;
import com.luisdev.heladopoints.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    @Value("${JWT_SECRET}")
    private String apiSecret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("LuismyDev")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            log.warn("Error creating JWT token for user: {}", user.getEmail(), exception);
            throw new TokenCreationException("Could not generate token");
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            return null;
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("LuismyDev")
                    .build()
                    .verify(token);

            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            log.warn("Invalid or expired token: {}", exception.getMessage());
            return null;
        }
    }


    private Instant getExpirationDate() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-05:00"));
    }

}
