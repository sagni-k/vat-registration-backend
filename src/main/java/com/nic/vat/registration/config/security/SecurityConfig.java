package com.nic.vat.registration.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

/**
 * Spring Security Configuration for JWT-based authentication.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    @Lazy
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the security filter chain with JWT authentication.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Publicly accessible endpoints
                        .requestMatchers(
                                "/auth/login",
                                "/auth/forgot-password",
                                "/auth/forgot-application"
                        ).permitAll()
                        // Optionally allow Part A registration without authentication
                        .requestMatchers(HttpMethod.POST, "/registration/part-a").permitAll()
                        // Swagger/OpenAPI docs
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        // All other requests require authentication
                        .anyRequest().authenticated())
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Bean for password encoding.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
