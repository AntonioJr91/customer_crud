package com.antoniojr.customer_crud.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.antoniojr.customer_crud.entity.Role;
import com.antoniojr.customer_crud.entity.User;
import com.antoniojr.customer_crud.jwt.JwtAccessDeniedHandler;
import com.antoniojr.customer_crud.jwt.JwtAuthenticationEntryPoint;
import com.antoniojr.customer_crud.jwt.JwtBlacklistFilter;
import com.antoniojr.customer_crud.repositories.RoleRepository;
import com.antoniojr.customer_crud.repositories.UserRepository;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class SecurityConfig {

  @Value("${jwt.private.key}")
  private RSAPrivateKey privateKey;

  @Value("${jwt.public.key}")
  private RSAPublicKey publicKey;

  @Autowired
  private JwtAuthenticationEntryPoint authEntryPoint;

  @Autowired
  private JwtBlacklistFilter blacklistFilter;

  @Autowired
  private JwtAccessDeniedHandler accessDeniedHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers("/h2-console/**").permitAll()
        .requestMatchers("/login", "/register").permitAll()
        .requestMatchers(HttpMethod.POST, "/logout").authenticated()
        .requestMatchers(HttpMethod.GET, "/customers/**").permitAll()
        .anyRequest().authenticated())
        .csrf(csrf -> csrf.disable())
        .headers(headers -> headers.frameOptions(frame -> frame.disable()))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(authEntryPoint)
            .accessDeniedHandler(accessDeniedHandler))
        .oauth2ResourceServer(oauth2 -> oauth2
            .authenticationEntryPoint(authEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

    http.addFilterBefore(blacklistFilter,
        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
    http.logout(logout -> logout.disable());
    return http.build();
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter scopeConverter = new JwtGrantedAuthoritiesConverter();
    scopeConverter.setAuthorityPrefix("SCOPE_");
    scopeConverter.setAuthoritiesClaimName("scope");

    JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
    jwtConverter.setJwtGrantedAuthoritiesConverter(scopeConverter);

    return jwtConverter;
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(privateKey).build();
    var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  CommandLineRunner initialSeed(UserRepository userRepo, RoleRepository roleRepo) {
    return args -> {

      if (roleRepo.count() > 0) {
        return;
      }

      Role roleUser = new Role(null, "NORMAL");
      Role roleAdmin = new Role(null, "ADMIN");
      roleRepo.save(roleUser);
      roleRepo.save(roleAdmin);

      if (userRepo.findByUsername("admin@admin.com").isEmpty()) {
        User admin = new User();
        admin.setUsername("admin@admin.com");
        admin.setPassword("$2y$10$JLcKQWYJUAF54iIEuR.p6.XKVxuorNeGjtVUIrt7G/nYtjNOqjyDS"); // 123456
        admin.getRoles().add(roleAdmin);

        userRepo.save(admin);
      }

      System.out.println(">>> Initial seed successfully.");
    };
  }

}
