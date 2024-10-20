package com.arturo.springboot.security.app.springbootcrud.repositories;

import com.arturo.springboot.security.app.springbootcrud.entities.Role;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

  Optional<Role> findByName(String name);
}
