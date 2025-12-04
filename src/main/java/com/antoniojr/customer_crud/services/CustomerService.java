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

import jakarta.persistence.EntityNotFoundException;

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

  @Transactional
  public CustomerDTO insert(CustomerDTO entityDTO) {
    Customer customer = new Customer();
    customer.setName(entityDTO.getName());
    customer.setCpf(entityDTO.getCpf());
    customer.setIncome(entityDTO.getIncome());
    customer.setBirthDate(entityDTO.getBirthDate());
    customer.setChildren(entityDTO.getChildren());

    customer = repository.save(customer);

    return new CustomerDTO(customer);
  }

  @Transactional
  public CustomerDTO update(int id, CustomerDTO entityDTO) {
    try {
      Customer entity = repository.getReferenceById(id);
      entity.setName(entityDTO.getName());
      entity.setCpf(entityDTO.getCpf());
      entity.setIncome(entityDTO.getIncome());
      entity.setBirthDate(entityDTO.getBirthDate());
      entity.setChildren(entityDTO.getChildren());

      entity = repository.save(entity);
      return new CustomerDTO(entity);

    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
  }
}
