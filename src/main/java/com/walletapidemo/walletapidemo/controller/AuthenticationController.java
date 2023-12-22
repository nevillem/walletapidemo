package com.walletapidemo.walletapidemo.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // @PostMapping
    // public ResponseEntity <AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request){
    //  return ResponseEntity.ok(service.refreshToken(request)) ;
    // }

    
}
