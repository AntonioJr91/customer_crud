package com.antoniojr.customer_crud.dto;

import java.io.Serializable;
import java.util.List;

import com.antoniojr.customer_crud.entity.User;

public class UserDTO implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private Integer id;
  private String username;
  private List<RoleDTO> roles;

  public UserDTO(Integer id, String username) {
    this.id = id;
    this.username = username;
  }

  public UserDTO(User entity) {
    id = entity.getId();
    username = entity.getUsername();
    roles = entity.getRoles().stream().map(role -> new RoleDTO(role.getId(), role.getName())).toList();
  }

  public UserDTO() {
  }

  public Integer getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public List<RoleDTO> getRoles() {
    return roles;
  }

}
