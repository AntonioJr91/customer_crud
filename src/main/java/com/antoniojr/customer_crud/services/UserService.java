package com.antoniojr.customer_crud.services;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.antoniojr.customer_crud.dto.UserDTO;
import com.antoniojr.customer_crud.dto.UserRegisterDTO;
import com.antoniojr.customer_crud.dto.UserUpdateDTO;
import com.antoniojr.customer_crud.entity.Role;
import com.antoniojr.customer_crud.entity.User;
import com.antoniojr.customer_crud.repositories.RoleRepository;
import com.antoniojr.customer_crud.repositories.UserRepository;
import com.antoniojr.customer_crud.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

  private UserRepository repository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;

  public UserService(UserRepository repository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional(readOnly = true)
  public Page<UserDTO> findAllPaged(Pageable pageable) {
    if (pageable == null) {
      throw new IllegalArgumentException("pageRequest must not be null");
    }

    Page<User> list = repository.findAll(pageable);
    return list.map(user -> new UserDTO(user));
  }

  @Transactional(readOnly = true)
  public UserDTO findById(int id) {
    Optional<User> obj = repository.findById(id);

    User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
    return new UserDTO(entity);
  }

  @Transactional(readOnly = true)
  public User findByUsername(String username) {
    return repository.findByUsername(username).orElse(null);
  }

  @Transactional
  public UserDTO insert(UserRegisterDTO entityDTO) {
    Optional<User> userExists = repository.findByUsername(entityDTO.getUsername());

    if (userExists.isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
    }

    User user = new User();

    Role defaultRole = roleRepository.findByName("NORMAL")
        .orElseThrow(() -> new RuntimeException("Default role not found."));

    user.getRoles().add(defaultRole);

    copyToDto(user, entityDTO);

    user = repository.save(user);

    return new UserDTO(user);
  }

  @Transactional
  public UserDTO update(int id, UserUpdateDTO entityDTO) {
    try {
      User user = repository.getReferenceById(id);

      Optional<User> userExists = repository.findByUsername(entityDTO.getUsername());
      if (userExists.isPresent() && !userExists.get().getId().equals(id)) {
        throw new DataIntegrityViolationException("Username already exists");
      }

      user.setUsername(entityDTO.getUsername());

      if (entityDTO.getPassword() != null && !entityDTO.getPassword().isBlank()) {
        user.setPassword(passwordEncoder.encode(entityDTO.getPassword()));
      }
      user = repository.save(user);

      return new UserDTO(user);

    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
  }

  @Transactional
  public void delete(int id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
  }

  private void copyToDto(User user, UserRegisterDTO entityDTO) {
    user.setUsername(entityDTO.getUsername());
    user.setPassword(passwordEncoder.encode(entityDTO.getPassword()));
  }
}
