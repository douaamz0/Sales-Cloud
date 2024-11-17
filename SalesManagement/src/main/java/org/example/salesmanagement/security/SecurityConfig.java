package org.example.salesmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Optionnel si vous utilisez des annotations de sécurité sur les méthodes
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Désactive la protection CSRF
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**").permitAll() // Permet l'accès aux routes publiques
                        .anyRequest().authenticated() // Authentifie toutes les autres requêtes
                )
                .addFilterBefore(new CustomHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Ajoute un filtre personnalisé

        return http.build();
    }
}


