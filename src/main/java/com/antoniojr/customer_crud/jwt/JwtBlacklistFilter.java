package com.antoniojr.customer_crud.jwt;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtBlacklistFilter extends OncePerRequestFilter {

  private final TokenBlackList tokenBlacklist;

  public JwtBlacklistFilter(TokenBlackList tokenBlacklist) {
    this.tokenBlacklist = tokenBlacklist;
  }

  @Override
  @SuppressWarnings("null")
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String token = request.getHeader("Authorization");

    if (token != null) {
      token = token.replace("Bearer ", "");

      if (tokenBlacklist.isRevoked(token)) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }
}
