package com.app.movie_app_backend.auth;

import com.app.movie_app_backend.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.app.movie_app_backend.service.UserDetailsServiceImpl;
import com.app.movie_app_backend.service.JwtService;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final UserDetailsServiceImpl userDetailsService;
  private final ObjectMapper objectMapper;

  public JwtAuthFilter(UserDetailsServiceImpl userDetailsService, ObjectMapper objectMapper) {
    this.userDetailsService = userDetailsService;
    this.objectMapper = objectMapper;

  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String authHeader = request.getHeader("Authorization");
      String token = null;
      String username = null;
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
        username = JwtService.extractUsername(token);
      }

      if (token == null) {
        filterChain.doFilter(request, response);
        return;
      }

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (JwtService.validateToken(token, userDetails)) {
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
              null, null);
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
      filterChain.doFilter(request, response);
    } catch (Exception e) {

      response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    }
  }
}