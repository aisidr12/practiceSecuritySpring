package com.arturo.springboot.security.app.springbootcrud.validation;

import com.arturo.springboot.security.app.springbootcrud.repositories.UserRepository;
import com.arturo.springboot.security.app.springbootcrud.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ExistUsername implements ConstraintValidator<ExistByUsername, String> {

  private final UserService userService;

  public ExistUsername(UserService userService) {
    this.userService = userService;
  }

  @Override
  public boolean isValid(String username, ConstraintValidatorContext context) {
    if(userService == null){
      return true;
    }
    boolean existe = !userService.existsByUsername(username);
    return existe;
  }
}
