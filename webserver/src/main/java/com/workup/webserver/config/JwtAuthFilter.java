package com.workup.webserver.config;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  @Autowired private JwtService jwtService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException, java.io.IOException {
    try {
      String authHeader = request.getHeader("Authorization");
      String token = null;
      String username = null;
      if (authHeader != null && authHeader.startsWith("Bearer")) {
        token = authHeader.substring(7);
        try {
          username = jwtService.extractUsername(token);
        } catch (Exception e) {
          System.out.println(e.getMessage());
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
          return;
        }
      }

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        if (jwtService.validateToken(token)) {
          // Create a simple authentication token based on the username
          String role = jwtService.extractClaim(token, claims -> claims.get("role", String.class));
          List<GrantedAuthority> authorities =
              Collections.singletonList(new SimpleGrantedAuthority(role));

          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(
                  username, null, authorities // No granted authorities
                  );
          SecurityContextHolder.getContext().setAuthentication(authToken);
          // get userId from claims
          String userId =
              jwtService.extractClaim(token, claims -> claims.get("userId", String.class));
          // System.out.println("userId: " + userId);
          // set it in the request body
          request.setAttribute("userId", userId);
        } else {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
          return;
        }
      }

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
    }
  }
}
