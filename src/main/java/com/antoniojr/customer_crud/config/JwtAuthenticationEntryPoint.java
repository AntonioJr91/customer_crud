package com.antoniojr.customer_crud.config;

import java.io.IOException;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.antoniojr.customer_crud.exceptions.StandardError;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper mapper;

  public JwtAuthenticationEntryPoint(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void commence(HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException) throws IOException {

    StandardError error = new StandardError();
    error.setTimestamp(Instant.now());
    error.setStatus(HttpStatus.UNAUTHORIZED.value());
    error.setError("Unauthorized.");
    error.setMsg(authException.getMessage());
    error.setPath(request.getRequestURI());

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    mapper.writeValue(response.getWriter(), error);
  }
}
