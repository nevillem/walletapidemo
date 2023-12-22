package com.walletapidemo.walletapidemo.controller;

import com.walletapidemo.walletapidemo.security.JwtService;
import com.walletapidemo.walletapidemo.entity.User;
import com.walletapidemo.walletapidemo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.walletapidemo.walletapidemo.entity.Role;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    // place or class that will implement register and authenticate
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder; 
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    //this create user and return the created token
    public boolean exist(String email){
        return repository.existsByEmail(email);
     }

    public AuthenticationResponse register(RegisterRequest request) {
        var user=User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.ROLE_USER)
        .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return  AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
    }
    
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        var user =repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken= jwtService.generateToken(user);
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            var refreshToken = jwtService.generateRefreshToken(user);
            return AuthenticationResponse.builder().accessToken(jwtToken)
                    .refreshToken(refreshToken).build();
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }

        // return AuthenticationResponse.builder()
        // .token(jwtToken.)
        // .build();
    }

    // public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
    //     return refreshTokenService.findByToken(request.getToken()).map(refreshTokenService::verifyExpiration)
    //             .map(refreshTokenService::verifyExpiration)
    //             .map(RefreshToken::getUserInfo)
    //             .map(userInfo -> {
    //                 var user =repository.findByEmail(userInfo.getEmail()).orElseThrow();
    //                 String accessToken = jwtService.generateToken(user);
    //                 return AuthenticationResponse.builder()
    //                         .accessToken(accessToken)
    //                         .token(request.getToken())
    //                         .build();
    //             }).orElseThrow(() -> new RuntimeException(
    //                     "Refresh token is not in database!"));
    // }    
}
