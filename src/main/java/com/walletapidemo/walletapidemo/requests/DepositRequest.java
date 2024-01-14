package com.walletapidemo.walletapidemo.requests;

import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {
    private Integer amount;
    private String accountId;     
    // private String pin;


    public Long getReferenceCode() {
    long min = 1000000000000L; //13 digits inclusive
    long max = 10000000000000L; //14 digits exclusive
    Random random = new Random();
    long number = min+((long)(random.nextDouble()*(max-min)));

    // this will convert any number sequence into 9 character.
    return number;
}
}
