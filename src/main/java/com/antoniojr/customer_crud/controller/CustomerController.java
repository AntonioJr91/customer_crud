package com.antoniojr.customer_crud.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.antoniojr.customer_crud.dto.CustomerDTO;
import com.antoniojr.customer_crud.services.CustomerService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @GetMapping
  public ResponseEntity<Page<CustomerDTO>> findAll(
      @NonNull @RequestParam(value = "page", defaultValue = "1") Integer page,
      @NonNull @RequestParam(value = "sizePage", defaultValue = "5") Integer sizePage,
      @NonNull @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
      @NonNull @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

    PageRequest pageRequest = PageRequest.of(page - 1,
        sizePage,
        Direction.valueOf(direction),
        orderBy);

    Page<CustomerDTO> list = customerService.findAllPaged(pageRequest);

    return ResponseEntity.ok(list);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<CustomerDTO> findById(@PathVariable int id) {
    CustomerDTO customerDto = customerService.findById(id);
    return ResponseEntity.ok(customerDto);
  }

  @PostMapping
  public ResponseEntity<CustomerDTO> insert(@RequestBody CustomerDTO entityDTO) {
    entityDTO = customerService.insert(entityDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entityDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(entityDTO);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<CustomerDTO> update(@PathVariable int id, @RequestBody CustomerDTO entityDTO) {
    entityDTO = customerService.update(id, entityDTO);
    return ResponseEntity.ok(entityDTO);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<CustomerDTO> delete(@PathVariable int id) {
    customerService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
