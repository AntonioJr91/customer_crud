package com.antoniojr.customer_crud.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antoniojr.customer_crud.dto.CustomerDTO;
import com.antoniojr.customer_crud.services.CustomerService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerResource {

  @Autowired
  private CustomerService customerService;

  @GetMapping
  public ResponseEntity<List<CustomerDTO>> findAll() {

    List<CustomerDTO> list = customerService.findAll();
    return ResponseEntity.ok(list);
  }
}
