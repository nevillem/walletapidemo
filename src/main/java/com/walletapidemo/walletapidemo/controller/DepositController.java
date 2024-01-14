package com.walletapidemo.walletapidemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walletapidemo.walletapidemo.controller.services.MemberAccountService;
import com.walletapidemo.walletapidemo.repository.UserRepository;
import com.walletapidemo.walletapidemo.requests.DepositRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class DepositController {
    private final UserRepository repository;
    private final MemberAccountService service;

    @PutMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositRequest request)throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        var user = repository.findByCustomernumber(username)
        .orElseThrow();
        return ResponseEntity.ok(service.customerDeposit(request,user.getId()));

    }

}
