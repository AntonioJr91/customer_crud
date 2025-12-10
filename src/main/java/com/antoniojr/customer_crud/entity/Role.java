package com.antoniojr.customer_crud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer roleId;
  private String name;

  public Role() {

  }

  public Role(Integer id, String name) {
    this.roleId = id;
    this.name = name;
  }

  public Integer getId() {
    return roleId;
  }

  public void setId(Integer id) {
    this.roleId = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Role other = (Role) obj;
    if (roleId == null) {
      if (other.roleId != null)
        return false;
    } else if (!roleId.equals(other.roleId))
      return false;
    return true;
  }
}
