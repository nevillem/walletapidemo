package com.walletapidemo.walletapidemo.controller.services;

import com.walletapidemo.walletapidemo.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.walletapidemo.walletapidemo.entity.User;
import com.walletapidemo.walletapidemo.enumess.Statuses;
import com.walletapidemo.walletapidemo.enumess.TokenType;
import com.walletapidemo.walletapidemo.reponse.AuthenticationResponse;
import com.walletapidemo.walletapidemo.repository.MemberAccountsRepository;
import com.walletapidemo.walletapidemo.repository.TokenRepository;
import com.walletapidemo.walletapidemo.repository.UserRepository;
import com.walletapidemo.walletapidemo.requests.AuthenticationRequest;
import com.walletapidemo.walletapidemo.requests.EmailDetails;
import com.walletapidemo.walletapidemo.requests.RegisterRequest;
import com.walletapidemo.walletapidemo.entity.Token;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import org.springframework.http.HttpHeaders;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletapidemo.walletapidemo.entity.MemberAccount;
import com.walletapidemo.walletapidemo.entity.Role;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    // place or class that will implement register and authenticate
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final MemberAccountsRepository memberAccountsRepository;
    private final PasswordEncoder passwordEncoder; 
    private final JwtService jwtService;
    // private RefreshTokenService refreshTokenService;
   private final EmailService emailService;

    private final AuthenticationManager authenticationManager;
    //this create user and return the created token
    public boolean exist(String email){
        return repository.existsByEmail(email);
     }

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
      var password =  request.getRandomNumberString();

        var user=User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .customernumber(request.getCustomerid())
        .pin(passwordEncoder.encode(password))
        .role(Role.ROLE_USER)
        .build();
        System.out.println(password);

        emailService.sendEmail(EmailDetails.builder().msgBody("<html><body>Please use these credentials to login!<br> Username:"+request.getCustomerid()+" <br>Pin: "+password)
        .recipient(request.getEmail())
        .subject("Customer ID and  PIN ")
        .build());
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        memberAccount(savedUser);
        return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
    }
    

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getCustomerid(),
              request.getPin()
          )
      );
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getCustomerid(), request.getPin()));   
      if (authentication.isAuthenticated()) {
      var user = repository.findByCustomernumber(request.getCustomerid())
          .orElseThrow();
            var jwtToken= jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthenticationResponse.builder().accessToken(jwtToken)
                    .refreshToken(refreshToken).build();
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }       
    }


    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
          return;
        validUserTokens.forEach(token -> {
          token.setExpired(true);
          token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
      }
      
    private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
   }

    private void memberAccount(User user) {
    var memberAccount = MemberAccount.builder()
        .user(user)
        .accountbalance(0)
        .statuses(Statuses.ACTIVE)
        .build();
    memberAccountsRepository.save(memberAccount);
   }
   
  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByCustomernumber(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }   
}
