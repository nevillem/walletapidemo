package com.walletapidemo.walletapidemo.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAccountResponse {
Integer accountId;
Integer accountBalace;


}
