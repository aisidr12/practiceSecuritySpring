package com.arturo.springboot.security.app.springbootcrud.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonCreator {
  //tenemos que injectar el atributo role del json
  @JsonCreator
  public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role){

  }
}
