package com.antoniojr.customer_crud.dto;

public class LoginResponseDTO {

  private String accessToken;
  private Long expiresIn;

  public LoginResponseDTO() {
  }

  public LoginResponseDTO(String accessToken, Long expiresIn) {
    this.accessToken = accessToken;
    this.expiresIn = expiresIn;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public Long getExpiresIn() {
    return expiresIn;
  }
}
