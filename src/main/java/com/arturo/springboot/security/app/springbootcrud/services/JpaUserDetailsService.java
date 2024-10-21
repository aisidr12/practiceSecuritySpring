package com.arturo.springboot.security.app.springbootcrud.services;

import com.arturo.springboot.security.app.springbootcrud.entities.User;
import com.arturo.springboot.security.app.springbootcrud.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This Class is used to load the user from the database and load
 * the user roles and permissions.
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public JpaUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException(
          String.format("Username  %s no exist in the system", username));
    }
    User userFound = user.orElseThrow();
    List<SimpleGrantedAuthority> authorities = userFound.getRoles().stream()
        .map(t -> new SimpleGrantedAuthority(t.getName())).toList();
    //Here we encapsulate the user data in the UserDetails object that Spring Security uses
    return new org.springframework.security.core.userdetails.User(userFound.getUsername(),
        userFound.getPassword(), userFound.isEnabled(), true, true, true, authorities);

  }
}
