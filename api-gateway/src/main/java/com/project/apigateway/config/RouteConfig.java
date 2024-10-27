package com.project.apigateway.config;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

@Configuration
public class RouteConfig {

    @Bean
    public RouterFunction<ServerResponse> studentServiceRoute() {
        return GatewayRouterFunctions
                .route("student-service")
                .route(RequestPredicates.path("/student/**"), HandlerFunctions.http("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> universityServiceRoute() {
        return GatewayRouterFunctions
                .route("university-service")
                .route(RequestPredicates.path("/university/**"), HandlerFunctions.http("http://localhost:8082"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> companyServiceRoute() {
        return GatewayRouterFunctions
                .route("company-service")
                .route(RequestPredicates.path("/company/**"), HandlerFunctions.http("http://localhost:8083"))
                .build();
    }
}
