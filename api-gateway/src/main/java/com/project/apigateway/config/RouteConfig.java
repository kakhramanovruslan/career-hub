package com.project.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Value("${spring.cloud.gateway.routes.student-server-url}")
    private String studentServiceUrl;

    @Value("${spring.cloud.gateway.routes.university-server-url}")
    private String universityServiceUrl;

    @Value("${spring.cloud.gateway.routes.company-server-url}")
    private String companyServiceUrl;

    @Value("${spring.cloud.gateway.routes.auth-server-url}")
    private String authServiceUrl;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("student-service", r -> r.path("/student/**")
                        .uri(studentServiceUrl))
                .route("university-service", r -> r.path("/university/**")
                        .uri(universityServiceUrl))
                .route("company-service", r -> r.path("/company/**")
                        .uri(companyServiceUrl))
                .route("auth-service", r -> r.path("/auth/**")
                        .uri(authServiceUrl))
                .build();
    }
}