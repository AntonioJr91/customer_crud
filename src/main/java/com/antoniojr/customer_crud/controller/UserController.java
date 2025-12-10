package com.antoniojr.customer_crud.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.antoniojr.customer_crud.dto.UserDTO;
import com.antoniojr.customer_crud.dto.UserRegisterDTO;
import com.antoniojr.customer_crud.dto.UserUpdateDTO;
import com.antoniojr.customer_crud.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('SCOPE_NORMAL') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {

    Page<UserDTO> list = userService.findAllPaged(pageable);

    return ResponseEntity.ok(list);
  }

  @GetMapping(value = "/{id}")
  @PreAuthorize("hasAuthority('SCOPE_NORMAL') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<UserDTO> findById(@PathVariable int id) {
    UserDTO customerDto = userService.findById(id);
    return ResponseEntity.ok(customerDto);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserRegisterDTO entityDTO) {
    UserDTO userDTO = userService.insert(entityDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(userDTO);
  }

  @PutMapping(value = "/{id}")
  @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<UserDTO> update(@PathVariable int id, @Valid @RequestBody UserUpdateDTO entityDTO) {
    UserDTO userDTO = userService.update(id, entityDTO);
    return ResponseEntity.ok(userDTO);
  }

  @DeleteMapping(value = "/{id}")
  @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable int id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
