package com.workup.webserver.config;

import com.workup.shared.enums.users.UserType;
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
  static final String ROLE_CLIENT = UserType.CLIENT.toString();
  static final String ROLE_FREELANCER = UserType.FREELANCER.toString();
  static final String ROLE_ADMIN = UserType.ADMIN.toString();

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(HttpMethod.OPTIONS)
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/users/login")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/users/clients/register")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/users/freelancers/register")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/jobs/search")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/jobs")
                    .hasAuthority(ROLE_CLIENT)
                    .requestMatchers(HttpMethod.GET, "/api/v1/jobs/me")
                    .hasAuthority(ROLE_CLIENT)
                    .requestMatchers(HttpMethod.POST, "/api/v1/jobs/{id}/proposals")
                    .hasAuthority(ROLE_FREELANCER)
                    .requestMatchers(HttpMethod.GET, "/api/v1/jobs/me/proposals")
                    .hasAuthority(ROLE_FREELANCER)
                    .requestMatchers(
                        HttpMethod.POST, "/api/v1/jobs/{jobId}/proposals/{proposalId}/accept")
                    .hasAuthority(ROLE_CLIENT)
                    .requestMatchers("/api/v1/payments/clients/**")
                    .hasAuthority(ROLE_CLIENT)
                    .requestMatchers("/api/v1/payments/freelancers/**")
                    .hasAuthority(ROLE_FREELANCER)
                    .requestMatchers(HttpMethod.POST, "/api/v1/payments/requests/{requestId}/pay")
                    .hasAuthority(ROLE_CLIENT)
                    .requestMatchers(HttpMethod.POST, "/api/v1/contracts/milestones/{id}/progress")
                    .hasAuthority(ROLE_FREELANCER)
                    .requestMatchers(HttpMethod.POST, "/api/v1/contracts/milestones/{id}/evaluate")
                    .hasAuthority(ROLE_CLIENT)
                    .requestMatchers(HttpMethod.POST, "/api/v1/contracts/terminations/{id}/handle")
                    .hasAuthority(ROLE_ADMIN)
                    .requestMatchers(HttpMethod.POST, "/api/v1/contracts/{id}/terminations/request")
                    .hasAnyAuthority(ROLE_CLIENT, ROLE_FREELANCER)
                    .requestMatchers("/api/v1/users/freelancer/**")
                    .hasAuthority(ROLE_FREELANCER)
                    .requestMatchers("/api/v1/users/client/**")
                    .hasAuthority(ROLE_CLIENT)
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(
            exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

    return http.build();
  }
}
