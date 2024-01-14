package com.walletapidemo.walletapidemo.controller.services;

import org.springframework.stereotype.Service;

import com.walletapidemo.walletapidemo.entity.Deposits;
import com.walletapidemo.walletapidemo.entity.MemberAccount;
import com.walletapidemo.walletapidemo.reponse.DepositResponse;
import com.walletapidemo.walletapidemo.reponse.MemberAccountResponse;
import com.walletapidemo.walletapidemo.repository.DepositRepository;
import com.walletapidemo.walletapidemo.repository.MemberAccountsRepository;
import com.walletapidemo.walletapidemo.requests.DepositRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAccountService {
 private final MemberAccountsRepository accountsRepo;
 private final DepositRepository depositRepository;

    public MemberAccountResponse getCustomerBalance(Integer id) {
        var memberaccount= accountsRepo.findByValidUser(id).orElseThrow();
        return MemberAccountResponse.builder().accountBalace(memberaccount.getAccountbalance()).build();
    }

    //deposit transaction
    public DepositResponse customerDeposit(DepositRequest request, Integer id) {
    var memberaccount= accountsRepo.findByValidUser(id).orElseThrow();
    var blance= memberaccount.getAccountbalance();
    var currentb= blance + request.getAmount();
    var referenceCode =  request.getReferenceCode();
        var balanceupdate=MemberAccount.builder()
        .accountbalance(currentb)
        .build();
        accountsRepo.save(balanceupdate);
        saveMemberAccount(memberaccount, request.getAmount(), referenceCode);

        return DepositResponse.builder().amount(request.getAmount()).balance(currentb).build();
    }
    private void saveMemberAccount(MemberAccount memberAccount, Integer amount, Long referenceCode) {
    var deposit = Deposits.builder()
        .deposit_amount(amount)
        .memberAccount(memberAccount)
        .desposit_reference(referenceCode)
        .build();
    depositRepository.save(deposit);
   }
}
