package com.arturo.springboot.security.app.springbootcrud.validation;

import com.arturo.springboot.security.app.springbootcrud.entities.User;
import com.arturo.springboot.security.app.springbootcrud.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ExistUsername implements ConstraintValidator<ExistByUsername, String> {

  private final UserRepository userRepository;

  public ExistUsername(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !Optional.ofNullable(userRepository.findByUsername(value)).isPresent();

  }


}
