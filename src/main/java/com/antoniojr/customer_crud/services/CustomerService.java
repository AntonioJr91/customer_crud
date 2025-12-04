package com.antoniojr.customer_crud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antoniojr.customer_crud.entity.Customer;
import com.antoniojr.customer_crud.repositories.CustomerRepository;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository repository;

  public List<Customer> findAll() {
    return repository.findAll();
  }
}
