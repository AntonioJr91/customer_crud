package com.antoniojr.customer_crud.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_customer")
public class Customer implements Serializable {
  private static final long serialVersionUID = 1L;

  private static Integer count = 0;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String cpf;
  private Double income;
  private LocalDate birthDate;
  private Integer children;

  @Column(columnDefinition = "DATETIME")
  private Instant createdAt;

  @Column(columnDefinition = "DATETIME")
  private Instant updatedAt;

  public Customer() {
  }

  public Customer(Integer id, String name, String cpf, Double income, LocalDate birthDate, Integer children) {
    validation(name, cpf, income, birthDate, children);
    this.id = ++count;
    this.name = name;
    this.cpf = cpf;
    this.income = income;
    this.birthDate = birthDate;
    this.children = children;
  }

  public Integer getId() {
    return id;
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

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  @PrePersist
  public void prePersist() {
    createdAt = Instant.now();
  }

  @PreUpdate
  public void preUpdate() {
    updatedAt = Instant.now();
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
    Customer other = (Customer) obj;
    if (cpf == null) {
      if (other.cpf != null)
        return false;
    } else if (!cpf.equals(other.cpf))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Customer [id=" + id + ", name=" + name + ", cpf=" + cpf + ", income=" + income + ", birthDate=" + birthDate
        + ", children=" + children + "]";
  }

  private void validation(String name, String cpf, Double income, LocalDate birthDate, Integer children) {

    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name must not be null or empty");
    }

    if (cpf == null || cpf.trim().isEmpty() || cpf.length() != 11 || !cpf.matches("\\d+")) {
      throw new IllegalArgumentException("CPF must be a numeric string of 11 digits");
    }

    if (income == null || income <= 0.0) {
      throw new IllegalArgumentException("Income must be greater than zero");
    }

    if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Birth date must not be in the future");
    }

    if (children != null && children < 0) {
      throw new IllegalArgumentException("Children must be zero or positive");
    }
  }

}
