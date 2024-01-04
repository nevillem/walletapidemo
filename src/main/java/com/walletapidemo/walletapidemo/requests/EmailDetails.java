package com.walletapidemo.walletapidemo.requests;

// Importing required classes
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
// Annotations
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
 
// Class
public class EmailDetails {
 
    // Class data members
    private String recipient;
    private String msgBody;
    private String subject;
    // private String attachment;
}
