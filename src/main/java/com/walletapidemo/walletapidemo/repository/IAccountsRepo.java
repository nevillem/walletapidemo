package com.walletapidemo.walletapidemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walletapidemo.walletapidemo.entity.Account;

@Repository
public interface IAccountsRepo extends JpaRepository<Account, Long>{

}
