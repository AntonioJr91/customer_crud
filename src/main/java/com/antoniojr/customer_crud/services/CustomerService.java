package com.antoniojr.customer_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antoniojr.customer_crud.dto.CustomerDTO;
import com.antoniojr.customer_crud.entity.Customer;
import com.antoniojr.customer_crud.repositories.CustomerRepository;
import com.antoniojr.customer_crud.services.exceptions.ResourceNotFoundException;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository repository;

  @Transactional(readOnly = true)
  public List<CustomerDTO> findAll() {
    List<Customer> list = repository.findAll();
    return list.stream().map(customer -> new CustomerDTO(customer)).toList();
  }

  @Transactional(readOnly = true)
  public CustomerDTO findById(int id) {
    Optional<Customer> obj = repository.findById(id);

    Customer entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
    return new CustomerDTO(entity);
  }
}
