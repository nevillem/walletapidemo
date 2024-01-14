package com.walletapidemo.walletapidemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.walletapidemo.walletapidemo.entity.MemberAccount;

public interface MemberAccountsRepository extends JpaRepository<MemberAccount, Integer>{
   @Query(value = """
      select m from MemberAccounts m, User u\s
      where u.id = :id AND m.user.id = u.id \s
      """)
      Optional<MemberAccount> findByValidUser(Integer id);
}
