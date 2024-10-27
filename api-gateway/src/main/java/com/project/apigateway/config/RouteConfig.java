package com.project.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

@Configuration
public class RouteConfig {

    @Value("${spring.cloud.gateway.routes.student-server-url}")
    private String studentServiceUrl;

    @Value("${spring.cloud.gateway.routes.university-server-url}")
    private String universityServiceUrl;

    @Value("${spring.cloud.gateway.routes.company-server-url}")
    private String companyServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> studentServiceRoute() {
        return GatewayRouterFunctions
                .route("student-service")
                .route(RequestPredicates.path("/student/**"), HandlerFunctions.http(studentServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> universityServiceRoute() {
        return GatewayRouterFunctions
                .route("university-service")
                .route(RequestPredicates.path("/university/**"), HandlerFunctions.http(universityServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> companyServiceRoute() {
        return GatewayRouterFunctions
                .route("company-service")
                .route(RequestPredicates.path("/company/**"), HandlerFunctions.http(companyServiceUrl))
                .build();
    }
}
