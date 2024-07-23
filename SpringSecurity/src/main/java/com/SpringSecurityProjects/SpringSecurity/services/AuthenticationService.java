package com.SpringSecurityProjects.SpringSecurity.services;

import com.SpringSecurityProjects.SpringSecurity.dto.JwtAuthenticationResponse;
import com.SpringSecurityProjects.SpringSecurity.dto.RefreshTokenRequest;
import com.SpringSecurityProjects.SpringSecurity.dto.SignInRequest;
import com.SpringSecurityProjects.SpringSecurity.dto.SignUpRequest;
import com.SpringSecurityProjects.SpringSecurity.entities.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
