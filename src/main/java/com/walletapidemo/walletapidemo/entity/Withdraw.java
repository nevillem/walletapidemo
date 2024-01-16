package com.walletapidemo.walletapidemo.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Withdraw {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer withdrew_amount;
  private long withdraw_reference;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "memberaccount_id")
  public MemberAccount memberAccount;
  @CreationTimestamp
  @Column(name="withdrew_at")
  private Date createdAt;

}
