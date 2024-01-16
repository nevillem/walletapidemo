package com.walletapidemo.walletapidemo.controller.services;

import org.springframework.stereotype.Service;

import com.walletapidemo.walletapidemo.reponse.MemberAccountResponse;
import com.walletapidemo.walletapidemo.repository.MemberAccountsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAccountService {
 private final MemberAccountsRepository accountsRepo;
    public MemberAccountResponse getCustomerBalance(Integer id) {
        var memberaccount= accountsRepo.findByValidUser(id).orElseThrow();
        return MemberAccountResponse.builder().accountBalace(memberaccount.getAccountbalance()).build();
    }
}
