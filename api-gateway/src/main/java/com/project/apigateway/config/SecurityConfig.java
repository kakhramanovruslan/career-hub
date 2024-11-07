package com.project.apigateway.config;

import com.project.apigateway.filter.SecurityFilter;
import com.project.apigateway.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenUtil jwtTokenUtil;

    @Bean
    public WebFilter jwtFilter() {
        return new SecurityFilter(jwtTokenUtil);
    }
}
