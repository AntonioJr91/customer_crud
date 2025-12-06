package com.antoniojr.customer_crud.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
  public Page<CustomerDTO> findAllPaged(PageRequest pageRequest) {
    if (pageRequest == null) {
      throw new IllegalArgumentException("pageRequest must not be null");
    }

    Page<Customer> list = repository.findAll(pageRequest);
    return list.map(customer -> new CustomerDTO(customer));
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
    copyToDto(customer, entityDTO);

    customer = repository.save(customer);

    return new CustomerDTO(customer);
  }

  @Transactional
  public CustomerDTO update(int id, CustomerDTO entityDTO) {
    try {
      Customer customer = repository.getReferenceById(id);
      
      copyToDto(customer, entityDTO);

      customer = repository.save(customer);

      return new CustomerDTO(customer);

    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
  }

  public void delete(int id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
  }

  private void copyToDto(Customer customer, CustomerDTO entityDTO) {
    customer.setName(entityDTO.getName());
    customer.setCpf(entityDTO.getCpf());
    customer.setIncome(entityDTO.getIncome());
    customer.setBirthDate(entityDTO.getBirthDate());
    customer.setChildren(entityDTO.getChildren());
  }
}
