package com.antoniojr.customer_crud.resource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antoniojr.customer_crud.entity.Customer;

@RestController
@RequestMapping(value = "/customers")
public class CustomerResource {

  @GetMapping
  public ResponseEntity<List<Customer>> findAll() {
    List<Customer> customers = new ArrayList<>();

    customers.add(new Customer("Ana Silva", "12345678901", 3500.0, LocalDate.of(1990, 5, 10), 1));
    customers.add(new Customer("Bruno Costa", "98765432100", 4200.0, LocalDate.of(1988, 3, 22), 0));
    customers.add(new Customer("Carlos Souza", "45678912322", 5000.0, LocalDate.of(1985, 1, 12), 2));
    customers.add(new Customer("Daniela Lima", "65432198744", 2800.0, LocalDate.of(1995, 9, 30), 0));
    customers.add(new Customer("Eduardo Gomes", "11122233344", 7200.0, LocalDate.of(1981, 2, 5), 3));
    customers.add(new Customer("Fernanda Rocha", "22233344455", 2600.0, LocalDate.of(1998, 6, 17), 0));
    customers.add(new Customer("Gabriel Santos", "33344455566", 3900.0, LocalDate.of(1992, 12, 3), 1));
    customers.add(new Customer("Helena Mendes", "44455566677", 3100.0, LocalDate.of(1994, 7, 25), 2));
    customers.add(new Customer("Igor Ferreira", "55566677788", 4500.0, LocalDate.of(1989, 10, 11), 0));
    customers.add(new Customer("Julia Carvalho", "66677788899", 3400.0, LocalDate.of(1996, 8, 8), 1));
    customers.add(new Customer("Lucas Almeida", "77788899900", 5100.0, LocalDate.of(1987, 4, 19), 2));
    customers.add(new Customer("Mariana Castro", "88899900011", 3800.0, LocalDate.of(1993, 11, 1), 0));
    customers.add(new Customer("Nicolas Barros", "99900011122", 6200.0, LocalDate.of(1984, 3, 14), 3));
    customers.add(new Customer("Olivia Pires", "00011122233", 2700.0, LocalDate.of(1997, 5, 2), 0));
    customers.add(new Customer("Paulo Teixeira", "10111213141", 4600.0, LocalDate.of(1991, 1, 27), 2));
    customers.add(new Customer("Quésia Ramos", "12131415161", 3300.0, LocalDate.of(1995, 9, 6), 1));
    customers.add(new Customer("Rafael Martins", "13141516171", 5800.0, LocalDate.of(1986, 6, 9), 3));
    customers.add(new Customer("Sofia Ribeiro", "14151617181", 2950.0, LocalDate.of(1999, 2, 23), 0));
    customers.add(new Customer("Tiago Araujo", "15161718191", 4100.0, LocalDate.of(1993, 7, 20), 1));
    customers.add(new Customer("Vitória Lopes", "16171819201", 3600.0, LocalDate.of(1994, 10, 5), 2));

    return ResponseEntity.ok(customers);
  }
}
