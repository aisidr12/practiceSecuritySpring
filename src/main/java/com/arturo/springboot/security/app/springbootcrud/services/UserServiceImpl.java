package com.arturo.springboot.security.app.springbootcrud.services;

import com.arturo.springboot.security.app.springbootcrud.entities.Role;
import com.arturo.springboot.security.app.springbootcrud.entities.User;
import com.arturo.springboot.security.app.springbootcrud.repositories.RoleRepository;
import com.arturo.springboot.security.app.springbootcrud.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;

  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
      RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> findAll() {
    return (List<User>) userRepository.findAll();
  }

  @Override
  @Transactional
  public User save(User user) {
    Optional<Role> roleUser = roleRepository.findByName("ROLE_USER");
    List<Role> roles = new ArrayList<>();
    roleUser.ifPresent(roles::add);
    if (user.isAdmin()) {
      Optional<Role> roleAdmin = roleRepository.findByName("ROLE_ADMIN");
      roleAdmin.ifPresent(roles::add);
    }
    user.setRoles(roles);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }
}
