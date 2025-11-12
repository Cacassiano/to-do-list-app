package dev.cassiano.to_do_api.infra.security;

import java.time.Instant;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import dev.cassiano.to_do_api.entities.User;

@Service
public class TokenService {

    @Value("${SECRET_KEY}")
    private String secretKey;
    @Value("${spring.application.name}")
    private String issuer;
    
    private final Long expirationInSecs =  (2*60)*60l;

    public String createToken(User user) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        return JWT.create()
            .withIssuer(issuer)
            .withSubject(user.getEmail())
            .withExpiresAt(this.getExpirationDate())
            .sign(algorithm);
    }

    public String getEmailFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        return JWT.require(algorithm)
            .build()
            .verify(token)
            .getSubject();
    }


    private Instant getExpirationDate(){
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime expirationDate = now.plusSeconds(expirationInSecs);
        System.out.println("Token's expiration date: "+expirationDate);
        return expirationDate.toInstant();
    }
}
