package com.walletapidemo.walletapidemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walletapidemo.walletapidemo.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);
}