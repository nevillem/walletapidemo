package com.walletapidemo.walletapidemo.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.walletapidemo.walletapidemo.enumess.Statuses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
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
public class MemberAccounts {
  @Id
  @GeneratedValue
  private Integer id;

  private Long accountbalance;
  
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Statuses statuses=Statuses.ACTIVE;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public User user;

  @CreationTimestamp
  @Column(name="created_at")
  private Date createdAt;
}
