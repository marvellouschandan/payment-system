package com.marvellous.paymentsystem.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderReq {
    Float amount;
    String currency;
    Map<String, String> notes = new HashMap<>();
}
