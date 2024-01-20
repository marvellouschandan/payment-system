package com.marvellous.paymentsystem.models.rzp.request;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class RzpCreateOrderRequest {
    Integer amount;
    String currency;
    String receipt;
    Map<String, String> notes;
}
