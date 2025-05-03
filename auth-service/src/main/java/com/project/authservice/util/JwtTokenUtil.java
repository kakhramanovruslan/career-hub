package com.project.authservice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import com.project.authservice.model.types.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

/**
 * Utility class for working with JWT (JSON Web Token).
 * This class provides methods for generating and validating JWT tokens.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    /**
     * Secret key used for signing JWT tokens. The value is injected from application properties.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * The lifetime of the JWT token in ISO-8601 duration format. The value is injected from application properties.
     */
    @Value("${jwt.lifetime}")
    private String jwtLifetime;

    /**
     * The issuer of the JWT token. The value is injected from application properties.
     */
    @Value("${jwt.issuer}")
    private String issuer;

    /**
     * Generates a JWT token for a user with a given ID and role.
     *
     * @param id The unique identifier of the user.
     * @param role The role of the user (e.g., ADMIN, USER).
     * @return A JWT token as a string.
     */
    public String generateToken(Long id, UserRole role) {
        Date expirationDate = new Date(new Date().getTime() + Duration.parse(jwtLifetime).toMillis());

        return JWT.create()
                .withSubject("user details")
                .withClaim("user-id", id)
                .withClaim("user-role", String.valueOf(role))
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * Validates a JWT token by verifying its signature and claims.
     *
     * @param token The JWT token to be validated.
     * @return true if the token is valid, false otherwise.
     */
    private boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer(issuer)
                    .build();

            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
