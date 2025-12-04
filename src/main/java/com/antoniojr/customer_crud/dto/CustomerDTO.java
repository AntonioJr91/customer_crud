package com.antoniojr.customer_crud.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.antoniojr.customer_crud.entity.Customer;

public class CustomerDTO implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String name;
  private String cpf;
  private Double income;
  private LocalDate birthDate;
  private Integer children;

  public CustomerDTO() {
  }

  public CustomerDTO(Integer id, String name, String cpf, Double income, LocalDate birthDate, Integer children) {
    this.id = id;
    this.name = name;
    this.cpf = cpf;
    this.income = income;
    this.birthDate = birthDate;
    this.children = children;
  }

  public CustomerDTO(Customer entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.cpf = entity.getCpf();
    this.income = entity.getIncome();
    this.birthDate = entity.getBirthDate();
    this.children = entity.getChildren();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public Double getIncome() {
    return income;
  }

  public void setIncome(Double income) {
    this.income = income;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public Integer getChildren() {
    return children;
  }

  public void setChildren(Integer children) {
    this.children = children;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
    CustomerDTO other = (CustomerDTO) obj;
    if (cpf == null) {
      if (other.cpf != null)
        return false;
    } else if (!cpf.equals(other.cpf))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "CustomerDTO [id=" + id + ", name=" + name + ", cpf=" + cpf + ", income=" + income + ", birthDate="
        + birthDate + ", children=" + children + "]";
  }

}
