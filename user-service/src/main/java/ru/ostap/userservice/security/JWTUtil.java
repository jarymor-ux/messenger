package ru.ostap.userservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${token.secret.key}")
    private String secret;
    @Value("${token.expiration_days}")
    private int expirationDays;

    public String generateToken(String username) {
        LocalDateTime expirationDateTime = LocalDateTime.now().plusDays(expirationDays);
        Date expirationDate = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("Messenger app")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("Messenger app")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}