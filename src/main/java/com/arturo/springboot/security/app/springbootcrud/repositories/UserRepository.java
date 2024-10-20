package com.arturo.springboot.security.app.springbootcrud.repositories;

import com.arturo.springboot.security.app.springbootcrud.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  User findByUsername(String username);

}
