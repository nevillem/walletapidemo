package com.walletapidemo.walletapidemo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    // interface already exists in spring
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain)
            throws ServletException, IOException {
                
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
        }
        //extrct token from header
        jwt=authHeader.substring(7);
        //extrct user email from jwttoken
        userEmail= jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // var isTokenValid = tokenRepository.findByToken(jwt)
            //     .map(t -> !t.isExpired() && !t.isRevoked())
            //     .orElse(false);
            if (jwtService.isTokenValid(jwt, userDetails)) {
             // this object need by spring ang secrity context holder
            // if user is valid we create an object of password and username
              UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities()
              );
            //we enforce the detials of authtoken
              authToken.setDetails(
                  new WebAuthenticationDetailsSource().buildDetails(request)
              );
            //update security context holder

              SecurityContextHolder.getContext().setAuthentication(authToken);
            }
          }

        // calling fillter chain dofilter to help use submit ti the next hand to be executed
          filterChain.doFilter(request, response);

    }

}
