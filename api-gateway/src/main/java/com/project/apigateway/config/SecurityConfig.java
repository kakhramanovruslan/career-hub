package com.project.apigateway.config;

import com.project.apigateway.filter.SecurityFilter;
import com.project.apigateway.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

/**
 * Configures security filter for JWT token validation in the API Gateway.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Registers a filter to check JWT tokens on incoming requests.
     *
     * @return {@link WebFilter} for JWT validation
     */
    @Bean
    public WebFilter jwtFilter() {
        return new SecurityFilter(jwtTokenUtil);
    }
}
