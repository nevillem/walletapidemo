package com.walletapidemo.walletapidemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walletapidemo.walletapidemo.entity.Withdraw;

public interface WithdrawRepository extends JpaRepository<Withdraw, Integer>{

}
