package com.walletapidemo.walletapidemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.walletapidemo.walletapidemo.entity.MemberAccount;
import jakarta.transaction.Transactional;

public interface MemberAccountsRepository extends JpaRepository<MemberAccount, Integer>{
   @Query(value = """
      select m from MemberAccount m, User u
      where u.id = :id AND m.user.id = u.id \s
      """)
      Optional<MemberAccount> findByValidUser(Integer id);

    @Transactional
    @Modifying
    @Query(value = "update MEMBER_ACCOUNT set accountbalance = accountbalance + :accountbalance  where id = :id", nativeQuery=true)
   //  void deposit(Integer id, Integer amount);
     void deposit(@Param("id") Integer id, @Param("accountbalance") Integer amount);

    @Transactional
    @Modifying
    @Query(value = "update MEMBER_ACCOUNT set accountbalance = accountbalance - :accountbalance  where id = :id", nativeQuery=true)
     void withdraw(@Param("id") Integer id, @Param("accountbalance") Integer amount);

}
