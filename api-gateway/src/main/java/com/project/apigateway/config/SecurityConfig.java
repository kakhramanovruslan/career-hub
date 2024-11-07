package com.project.apigateway.config;

import com.project.apigateway.filter.JwtAuthFilter;
import com.project.apigateway.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtTokenUtil jwtTokenUtil;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.POST, "/auth/registration").permitAll()
                        .pathMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterBefore(new JwtAuthFilter(jwtTokenUtil), SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }
}