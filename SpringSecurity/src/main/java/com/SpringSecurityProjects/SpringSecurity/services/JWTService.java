package com.SpringSecurityProjects.SpringSecurity.services;

import com.SpringSecurityProjects.SpringSecurity.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Objects;

public interface JWTService {
     String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    String genarateRefreshToken(HashMap<String, Object> extraclaims, UserDetails userDetails);
}
