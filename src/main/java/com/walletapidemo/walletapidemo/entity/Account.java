package com.walletapidemo.walletapidemo.entity;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name="accounts_tbl")
@Entity
@Setter
@Getter
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String account_name;
    private String account_code;
    private String account_types;
    private Float opening_balance;	
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
}
