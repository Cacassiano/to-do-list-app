package dev.cassiano.to_do_api.SpringSecurity.security.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import dev.cassiano.to_do_api.users.User;

@Service

public class TokenService {
    
    @Value("${ALGORITHM_KEY")
    private String key;

    @Value("${spring.application.name}")
    private String appName;

    public String generateToken(User user) throws Exception{
        try {
            Algorithm algoritmo = Algorithm.HMAC512(key);
            String token = JWT.create()
                        .withIssuer(appName)
                        .withSubject(user.getUsername())
                        .withExpiresAt(getExpireDate())
                        .sign(algoritmo);
            return token;
        } catch (JWTCreationException e ) {
            throw new Exception("Erro ao tentar gerar o token");
        }
    }

        public String userValidation(String token) throws Exception {
            try {
                Algorithm algoritmo = Algorithm.HMAC512(key);
                return JWT.require(algoritmo)
                    .withIssuer(appName)
                    .build()
                    .verify(token)
                    .getSubject();
            } catch (JWTVerificationException e) {
                throw new Exception("Invalido");
            }
        }


        private Instant getExpireDate() {
            return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        }

}
