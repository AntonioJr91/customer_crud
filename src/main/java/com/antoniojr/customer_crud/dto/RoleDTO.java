package com.antoniojr.customer_crud.dto;

import java.io.Serializable;

import com.antoniojr.customer_crud.entity.Role;

public class RoleDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer roleId;
  private String name;

  public RoleDTO() {
  }

  public RoleDTO(Integer roleId, String name) {
    this.roleId = roleId;
    this.name = name;
  }

  public RoleDTO(Role entity) {
    roleId = entity.getId();
    name = entity.getName();
  }

  public Integer getRoleId() {
    return roleId;
  }

  public String getName() {
    return name;
  }

}
