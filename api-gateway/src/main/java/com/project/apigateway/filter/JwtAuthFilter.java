package com.project.apigateway.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.apigateway.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class JwtAuthFilter implements WebFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = extractToken(exchange);

        try{
        if (token != null) {
            List<String> claims = jwtTokenUtil.validateTokenAndRetrieveClaims(token);

            if (claims != null && !claims.isEmpty()) {
                String userId = claims.get(0);
                String userRole = claims.get(1);

                exchange.getRequest()
                        .mutate()
                        .header("user-id", userId)
                        .header("user-role", userRole)
                        .build();
            }
        }
        }
        catch(JWTVerificationException e){
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            exchange.getResponse().getHeaders().set("Error", "Invalid token");
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    private String extractToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
