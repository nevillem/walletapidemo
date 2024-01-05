package com.walletapidemo.walletapidemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walletapidemo.walletapidemo.entity.MemberAccounts;

public interface MemberAccountsRepository extends JpaRepository<MemberAccounts, Integer>{

}
