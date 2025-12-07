package com.antoniojr.customer_crud;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.antoniojr.customer_crud.entity.Customer;

public class CustomerTests {
  Integer id = 1;
  String name = "John";
  String cpf = "12345678910";
  Double income = 100.0;
  LocalDate birthDate = LocalDate.of(1991, 1, 1);
  Integer children = 1;

  @Test
  void customerShouldCreatedWhenValid() {

    Customer customer = new Customer(id, name, cpf, income, birthDate, children);

    Assertions.assertEquals(id, customer.getId());
    Assertions.assertEquals(name, customer.getName());
    Assertions.assertEquals(cpf, customer.getCpf());
    Assertions.assertEquals(income, customer.getIncome());
    Assertions.assertEquals(birthDate, customer.getBirthDate());
    Assertions.assertEquals(children, customer.getChildren());
  }

  @Test
  void shouldThrowExceptionWhenNameIsNull() {
    name = null;

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Customer(id, name, cpf, income, birthDate, children);
    });
  }

  @Test
  void shouldThrowExceptionWhenNameIsBlank() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Customer(1, "   ", "12345678910", 100.0, LocalDate.of(1991, 1, 1), 1);
    });
  }

  @Test
  void shouldThrowExceptionWhenCpfIsInvalid() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Customer(1, "John", "abc", 100.0, LocalDate.of(1991, 1, 1), 1);
    });
  }

  @Test
  void shouldThrowExceptionWhenIncomeIsZeroOrNegative() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Customer(1, "John", "12345678910", -50.0, LocalDate.of(1991, 1, 1), 1);
    });
  }

  @Test
  void shouldThrowExceptionWhenBirthDateIsAfterToday() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Customer(1, "John", "12345678910", 100.0, LocalDate.now().plusDays(1), 1);
    });
  }

  @Test
  void shouldThrowExceptionWhenChildrenIsNegative() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Customer(1, "John", "12345678910", 100.0, LocalDate.of(1991, 1, 1), -1);
    });
  }

}
