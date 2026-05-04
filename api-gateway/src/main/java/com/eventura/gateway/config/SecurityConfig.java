package com.eventura.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Deshabilitamos CSRF para APIs
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/actuator/**").permitAll() // Exponemos métricas
                        .pathMatchers("/eureka/**").permitAll()
                        .anyExchange().authenticated() // Exigimos autenticación para el resto
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
                })); // Configura el validador JWT

        return http.build();
    }
}