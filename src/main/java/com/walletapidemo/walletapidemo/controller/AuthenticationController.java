package com.walletapidemo.walletapidemo.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walletapidemo.walletapidemo.controller.services.AuthenticationService;
import com.walletapidemo.walletapidemo.reponse.AuthenticationResponse;
import com.walletapidemo.walletapidemo.requests.AuthenticationRequest;
import com.walletapidemo.walletapidemo.requests.RegisterRequest;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {
  private final AuthenticationService service;
    
    @PostMapping("/signup")
    public ResponseEntity<?> register(
        @RequestBody RegisterRequest request){
         if(service.exist(request.getEmail())==true){
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

         }

        return ResponseEntity.ok(service.register(request));
    }

     @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody AuthenticationRequest request){
         return ResponseEntity.ok(service.authenticate(request));

    }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }

    
}
