package com.arturo.springboot.security.app.springbootcrud.services;

import com.arturo.springboot.security.app.springbootcrud.entities.User;
import java.util.List;

public interface UserService {

  List<User> findAll();

  User save(User user);
}
