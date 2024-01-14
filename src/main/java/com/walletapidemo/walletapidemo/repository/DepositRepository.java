package com.walletapidemo.walletapidemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walletapidemo.walletapidemo.entity.Deposits;

public interface DepositRepository extends JpaRepository<Deposits, Integer>{

}
