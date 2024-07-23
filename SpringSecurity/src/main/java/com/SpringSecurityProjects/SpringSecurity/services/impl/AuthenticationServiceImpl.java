package com.SpringSecurityProjects.SpringSecurity.services.impl;

import com.SpringSecurityProjects.SpringSecurity.dto.JwtAuthenticationResponse;
import com.SpringSecurityProjects.SpringSecurity.dto.RefreshTokenRequest;
import com.SpringSecurityProjects.SpringSecurity.dto.SignInRequest;
import com.SpringSecurityProjects.SpringSecurity.dto.SignUpRequest;
import com.SpringSecurityProjects.SpringSecurity.entities.Role;
import com.SpringSecurityProjects.SpringSecurity.entities.User;
import com.SpringSecurityProjects.SpringSecurity.repository.UserRepository;
import com.SpringSecurityProjects.SpringSecurity.services.AuthenticationService;
import com.SpringSecurityProjects.SpringSecurity.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.SignatureUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

        public User signUp(SignUpRequest signUpRequest){
        User user = new User();

        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userRepository.save(user);
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalStateException("Invalid username or password"));

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.genarateRefreshToken(new HashMap<>(),user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshTopken(refreshToken);

        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
            String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
            User user = userRepository.findByEmail(userEmail).orElseThrow();
            if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
                var jwt = jwtService.generateToken(user);

                JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

                jwtAuthenticationResponse.setToken(jwt);
                jwtAuthenticationResponse.setRefreshTopken(refreshTokenRequest.getToken());

                return jwtAuthenticationResponse;
            }
            return null;
    }
}
