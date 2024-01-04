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
public class RegisterRequest {

    private String firstname;
    private String lastname;    
    private String id;      
    private String customerid;     
    private String email;    
    // private String pin;


    public String getRandomNumberString() {
    // It will generate 6 digit random Number.
    // from 0 to 999999
    Random rnd = new Random();
    int number = rnd.nextInt(99999);
    var pinCode= String.format("%05d", number);

    // this will convert any number sequence into 6 character.
    return pinCode;
}

}
