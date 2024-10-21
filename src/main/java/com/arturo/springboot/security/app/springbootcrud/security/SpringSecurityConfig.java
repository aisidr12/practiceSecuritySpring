package com.arturo.springboot.security.app.springbootcrud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * This method is used to configure the security of the application giving access to specific endpoints
   * according if they are authenticated or not.
   * @param http
   * @return
   * @throws Exception
   */

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests( auth ->
            auth
                .requestMatchers(HttpMethod.GET,"/api/users").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/users/register").permitAll()
                .anyRequest().authenticated())
        .csrf(config -> config.disable())
        .sessionManagement(
            management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();

  }
}
