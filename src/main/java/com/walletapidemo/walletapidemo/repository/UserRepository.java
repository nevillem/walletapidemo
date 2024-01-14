package com.walletapidemo.walletapidemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.walletapidemo.walletapidemo.entity.User;




@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByCustomernumber(String customernumber);
  Boolean existsByEmail(String email);  
  Boolean existsByCustomernumber(String customernumber) ;
 @Query(value ="select m.accountbalance from MemberAccounts m, User WHERE  u.id = :id and m.user_id=:id",nativeQuery = true)
    Optional<User> findById(Integer id);
     
}
