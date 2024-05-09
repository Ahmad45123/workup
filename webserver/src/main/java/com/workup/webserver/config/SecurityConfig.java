package com.workup.webserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired private JwtAuthFilter authFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(
            exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(HttpMethod.OPTIONS)
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "api/v1/users/login")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/jobs/search")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/jobs")
                    .hasAuthority("ROLE_CLIENT")
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
