package com.project.apigateway.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.apigateway.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader("Authorization");

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (jwt.isBlank()) {
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token");
            } else {
                try {
                    List<String> claims = jwtTokenUtil.validateTokenAndRetrieveClaims(jwt);

                    if (claims != null) {
                        String id = claims.get(0);
                        String role = claims.get(1);

                        httpServletRequest.setAttribute("user-id", id);
                        httpServletRequest.setAttribute("user-role", role);
                    }
                } catch(JWTVerificationException e){
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token");
                }
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}