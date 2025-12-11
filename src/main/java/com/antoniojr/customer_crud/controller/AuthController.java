package com.antoniojr.customer_crud.controller;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.antoniojr.customer_crud.dto.LoginResponseDTO;
import com.antoniojr.customer_crud.dto.UserRegisterDTO;
import com.antoniojr.customer_crud.entity.LoginRequest;
import com.antoniojr.customer_crud.entity.Role;
import com.antoniojr.customer_crud.repositories.RoleRepository;
import com.antoniojr.customer_crud.services.UserService;

@RestController
public class AuthController {

  private JwtEncoder jwtEncoder;
  private UserService userService;
  private PasswordEncoder passwordEncoder;

  public AuthController(JwtEncoder jwtEncoder, UserService userService, RoleRepository roleRepository,
      PasswordEncoder passwordEncoder) {
    this.jwtEncoder = jwtEncoder;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody UserRegisterDTO newUser) {
    userService.insert(newUser);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest loginRequest) {
    var user = userService.findByUsername(loginRequest.getUsername());

    if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new BadCredentialsException("User or password is invalid.");
    }

    Long expiresIn = 300L;
    Instant now = Instant.now();

    var roles = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(" "));

    var claims = JwtClaimsSet.builder()
        .issuer("api")
        .subject(user.getId().toString())
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiresIn))
        .claim("scope", roles)
        .build();

    var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims));

    return ResponseEntity.ok(new LoginResponseDTO(jwtValue.getTokenValue(), expiresIn));

  }
}
