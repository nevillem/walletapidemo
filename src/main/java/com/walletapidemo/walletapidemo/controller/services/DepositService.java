package com.walletapidemo.walletapidemo.controller.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.walletapidemo.walletapidemo.entity.Deposits;
import com.walletapidemo.walletapidemo.entity.MemberAccount;
import com.walletapidemo.walletapidemo.entity.Withdraw;
import com.walletapidemo.walletapidemo.reponse.DepositResponse;
import com.walletapidemo.walletapidemo.reponse.WithdrawResponse;
import com.walletapidemo.walletapidemo.repository.DepositRepository;
import com.walletapidemo.walletapidemo.repository.MemberAccountsRepository;
import com.walletapidemo.walletapidemo.repository.UserRepository;
import com.walletapidemo.walletapidemo.repository.WithdrawRepository;
import com.walletapidemo.walletapidemo.requests.DepositRequest;
import com.walletapidemo.walletapidemo.requests.WithdrawRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepositService {
 private final MemberAccountsRepository accountsRepo;
 private final DepositRepository repDepositRepository;
 private final WithdrawRepository withdrawRepository;
 private final UserRepository repository;

    //deposit transaction
    public DepositResponse customerDeposit(DepositRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    var user = repository.findByCustomernumber(username)
        .orElseThrow();       
    var memberaccount= accountsRepo.findByValidUser(user.getId()).orElseThrow();
    accountsRepo.deposit(memberaccount.getId(), request.getAmount());
    System.out.println("id:"+memberaccount.getId());
    var referenceCode =  request.getReferenceCode();
        depositMemberAccount(memberaccount, request.getAmount(), referenceCode);

        return DepositResponse.builder().amount(request.getAmount()).balance(memberaccount.getAccountbalance()+request.getAmount()).build();
    }
    
    private void depositMemberAccount(MemberAccount memberAccount, Integer amount, Long referenceCode) {
    var deposit = Deposits.builder()
        .deposit_amount(amount)
        .memberAccount(memberAccount)
        .desposit_reference(referenceCode)
        .build();
    repDepositRepository.save(deposit);
   }
    public WithdrawResponse customerWithdraw(WithdrawRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    var user = repository.findByCustomernumber(username)
        .orElseThrow();       
    var memberaccount= accountsRepo.findByValidUser(user.getId()).orElseThrow();
    if (memberaccount.getAccountbalance() <=0) {
        
    }

    accountsRepo.withdraw(memberaccount.getId(), request.getAmount());
    var referenceCode =  request.getReferenceCode();
    withdrawMemberAccount(memberaccount, request.getAmount(), referenceCode);
    return WithdrawResponse.builder().amount(request.getAmount()).balance(memberaccount.getAccountbalance()-request.getAmount()).build();
   }  
     
    private void withdrawMemberAccount(MemberAccount memberAccount, Integer amount, Long referenceCode) {
    var withdraw = Withdraw.builder()
        .withdrew_amount(amount)
        .memberAccount(memberAccount)
        .withdraw_reference(referenceCode)
        .build();
    withdrawRepository.save(withdraw);
   }   
}
