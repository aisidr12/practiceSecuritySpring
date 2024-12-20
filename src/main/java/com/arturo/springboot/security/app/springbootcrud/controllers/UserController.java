package com.arturo.springboot.security.app.springbootcrud.controllers;

import com.arturo.springboot.security.app.springbootcrud.entities.User;
import com.arturo.springboot.security.app.springbootcrud.services.UserService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> list() {
    return userService.findAll();
  }


  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody User user,BindingResult result) {
    if(result.hasErrors()){
      return validation(result);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
  }

  @PostMapping("/register")
  //@PreAuthorize("hasRole('ADMIN')")
  //@PreAuthorize("hasAnyRole('ADMIN','USER')")
  public ResponseEntity<?> register(@Valid @RequestBody User user,BindingResult result) {
    user.setAdmin(false);
    return create(user,result);
  }

  private ResponseEntity<?> validation(BindingResult result) {
    Map<String, String> errors = new HashMap<>();

    result.getFieldErrors().forEach(err -> {
      errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }
}
