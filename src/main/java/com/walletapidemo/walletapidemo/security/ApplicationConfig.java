package com.walletapidemo.walletapidemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.walletapidemo.walletapidemo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
  private final UserRepository repository;

    // this class will hold all application configurations 
@Bean
public UserDetailsService userDetailsService(){
            // this represents a lambda
         return username-> repository.findByCustomernumber(username)    
         .orElseThrow(()-> new UsernameNotFoundException("user not found")); 
}

//provide application provider bean
@Bean
public AuthenticationProvider authenticationProvider(){
    // it's responsioble to fetch user details, ecode username and password
    DaoAuthenticationProvider authProvider= new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(setPasswordEncoder());
    return authProvider;
}

@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
    return config.getAuthenticationManager();
}

@Bean
public PasswordEncoder setPasswordEncoder() {
    return new BCryptPasswordEncoder();
}
}
