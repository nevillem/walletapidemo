package com.walletapidemo.walletapidemo.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositResponse {
  @JsonProperty("depositedamount")
  private Integer amount;
  @JsonProperty("balance")
  private Integer balance;
}
