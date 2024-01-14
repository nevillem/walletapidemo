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
public class Deposits {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer deposit_amount;
  private long desposit_reference;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "memberaccount_id")
  public MemberAccount memberAccount;
  @CreationTimestamp
  @Column(name="created_at")
  private Date createdAt;

}
