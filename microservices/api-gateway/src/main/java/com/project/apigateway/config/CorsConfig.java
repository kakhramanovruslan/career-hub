package com.project.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuration class for setting up Cross-Origin Resource Sharing (CORS) in the API Gateway.
 * Defines allowed origins, methods, headers, and credential settings for cross-origin requests.
 */
@Configuration
public class CorsConfig {

    @Value("${cors.allowCredentials}")
    private boolean allowCredentials;

    @Value("${cors.allowedOrigins}")
    private String[] allowedOrigins;

    @Value("${cors.allowedMethods}")
    private String[] allowedMethods;

    @Value("${cors.allowedHeaders}")
    private String[] allowedHeaders;

    /**
     * Creates and configures a {@link CorsWebFilter} to apply CORS settings to all incoming HTTP requests.
     *
     * @return a configured {@link CorsWebFilter} instance
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(allowCredentials);
        corsConfiguration.setAllowedOrigins(Arrays.stream(allowedOrigins).toList());
        corsConfiguration.setAllowedMethods(Arrays.stream(allowedMethods).toList());
        corsConfiguration.setAllowedHeaders(Arrays.stream(allowedHeaders).toList());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(source);
    }
}
