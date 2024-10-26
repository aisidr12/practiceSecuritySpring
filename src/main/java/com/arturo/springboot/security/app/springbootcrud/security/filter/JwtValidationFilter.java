package com.arturo.springboot.security.app.springbootcrud.security.filter;

import com.arturo.springboot.security.app.springbootcrud.security.SimpleGrantedAuthorityJsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtValidationFilter extends BasicAuthenticationFilter {


  public JwtValidationFilter(
      AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    //1.- Get the header from the request
    String header = request.getHeader("Authorization");
    if (header == null || !header.startsWith("Bearer")) {
      //2.- If the header is not null and starts with Bearer, then we call the chain.doFilter method
      chain.doFilter(request, response);
      return;
    }

    String token = header.replace("Bearer ", "");
    try {
      Claims claims = Jwts.parser().verifyWith((SecretKey) JwtAuthenticationFilter.SECRET_KEY)
          .build().parseSignedClaims(token).getPayload();
      String username = claims.getSubject();
      // String username2 = (String) claims.get("username");
      Object authorities = claims.get("authorities");

      //Obtains roles
      Collection<? extends GrantedAuthority> roles = Arrays.asList(
          new ObjectMapper()
              .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
              .readValue(authorities.toString().getBytes(),
                  SimpleGrantedAuthority[].class));

      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          username, null, roles);
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      chain.doFilter(request, response);
    } catch (JwtException e) {
      Map<String, String> body = new HashMap<>();
      body.put("error", e.getMessage());
      body.put("message", "The token is not valid");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write(new ObjectMapper().writeValueAsString(body));
      response.setContentType("application/json");
    }

    //Remember, once you done this filter,
    // you have to add it to the security configuration as a new filter

  }
}
