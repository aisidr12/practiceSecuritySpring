package com.arturo.springboot.security.app.springbootcrud.security.filter;

import com.arturo.springboot.security.app.springbootcrud.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class is responsible for authenticating the user and generating the JWT token
 * Basically, this class generate tokens according to the user credentials that is passed in the request
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private static final Key SECRET_KEY = Jwts.SIG.HS256.key().build();

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    User user = null;
    String username = null;
    String password = null;
    try {
      //Here we retrieve the user data from the request from the json and bind to the User object class
      user = new ObjectMapper().readValue(request.getInputStream(), User.class);
      username = user.getUsername();
      password = user.getPassword();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        user.getUsername(), user.getPassword());

    return authenticationManager.authenticate(authenticationToken);
    //This authenticate calls JpaUserDetailsService.loadUserByUsername method to validate the user in the back side
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {

    org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
    String username = user.getUsername();

    Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

    Claims claims = Jwts.claims()
        .add("authorities", roles)
        .add("username", username)
        .build();

    String token = Jwts.builder()
        .subject(username)
        .claims(claims)
        .expiration(new Date(System.currentTimeMillis() + 3600000))
        .issuedAt(new Date())
        .signWith(SECRET_KEY)
        .compact();

    response.addHeader("Authorization", "Bearer " + token);
    Map<String, String> body = new HashMap<>();
    body.put("token", token);
    body.put("username", username);
    body.put("message", String.format("User %s authenticated successfully", username));

    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_OK);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {

    Map<String, String> body = new HashMap<>();
    body.put("message", "Authentication failed, please check your credentials");
    body.put("error", failed.getMessage());

    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    response.setContentType("application/json");
    response.setStatus(401);
  }
}
