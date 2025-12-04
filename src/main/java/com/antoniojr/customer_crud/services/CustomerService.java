package com.antoniojr.customer_crud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antoniojr.customer_crud.dto.CustomerDTO;
import com.antoniojr.customer_crud.entity.Customer;
import com.antoniojr.customer_crud.repositories.CustomerRepository;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository repository;

  @Transactional(readOnly = true)
  public List<CustomerDTO> findAll() {
    List<Customer> list = repository.findAll();
    return list.stream().map(customer -> new CustomerDTO(customer)).toList();
  }
}
