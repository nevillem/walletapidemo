package com.walletapidemo.walletapidemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walletapidemo.walletapidemo.controller.services.DepositService;
import com.walletapidemo.walletapidemo.repository.UserRepository;
import com.walletapidemo.walletapidemo.requests.DepositRequest;
import com.walletapidemo.walletapidemo.requests.WithdrawRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class DepositController {
    private final UserRepository repository;
    private final DepositService service;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositRequest request)throws Exception {

        return ResponseEntity.ok(service.customerDeposit(request));

    }
  
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawRequest request)throws Exception {

        return ResponseEntity.ok(service.customerWithdraw(request));

    }
      

}
