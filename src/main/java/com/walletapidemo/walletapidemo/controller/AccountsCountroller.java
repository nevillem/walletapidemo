package com.walletapidemo.walletapidemo.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walletapidemo.walletapidemo.controller.services.MemberAccountService;
import com.walletapidemo.walletapidemo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class AccountsCountroller {
    private final UserRepository repository;
    private final MemberAccountService service;

    @GetMapping("/accountsbalance")
    public ResponseEntity<?> getAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        var user = repository.findByCustomernumber(username)
        .orElseThrow();
        return ResponseEntity.ok(service.getCustomerBalance(user.getId()));

    }

}
