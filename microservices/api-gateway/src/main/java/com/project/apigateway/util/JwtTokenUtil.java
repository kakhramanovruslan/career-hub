package com.project.apigateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Utility class for validating and parsing JWT tokens.
 */
@Component
public class JwtTokenUtil {

    /**
     * Secret key used to sign and verify JWT tokens.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Expected issuer of the JWT tokens.
     */
    @Value("${jwt.issuer}")
    private String issuer;

    /**
     * Validates the given JWT token and retrieves user claims.
     *
     * @param token the JWT token string
     * @return a list containing user ID and role
     * @throws JWTVerificationException if the token is invalid or expired
     */
    public List<String> validateTokenAndRetrieveClaims(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("user details")
                .withIssuer(issuer)
                .build();

        DecodedJWT jwt = verifier.verify(token);

        String userId = jwt.getClaim("user-id").asLong().toString();
        String userRole = jwt.getClaim("user-role").asString();

        return List.of(userId, userRole);
    }
}
