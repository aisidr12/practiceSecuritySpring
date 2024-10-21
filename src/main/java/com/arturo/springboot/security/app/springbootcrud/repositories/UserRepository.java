package com.arturo.springboot.security.app.springbootcrud.repositories;

import com.arturo.springboot.security.app.springbootcrud.entities.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByUsername(String username);

}
