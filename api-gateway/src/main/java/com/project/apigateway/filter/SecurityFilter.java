package com.project.apigateway.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.apigateway.model.types.UserRole;
import com.project.apigateway.util.JwtTokenUtil;
import com.project.apigateway.util.PathAccessList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class SecurityFilter implements WebFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (PathAccessList.isPathOnPublicPaths(path)) {
            return chain.filter(exchange);
        }

        String token = extractToken(exchange);

        try{
            if (token == null){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            else {
                List<String> claims = jwtTokenUtil.validateTokenAndRetrieveClaims(token);

                if (claims != null && !claims.isEmpty()) {
                    String userId = claims.get(0);
                    UserRole userRole = UserRole.valueOf(claims.get(1));

                    exchange.getRequest()
                            .mutate()
                            .header("X-User-Id", userId)
                            .header("X-User-Role", userRole.name())
                            .build();
                }
            }
        }
        catch(JWTVerificationException e){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
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
