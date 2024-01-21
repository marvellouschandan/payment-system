package com.marvellous.paymentsystem.models.rzp.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RzpCapturePaymentResponse {
    String razorpay_payment_id;
    String razorpay_order_id;
    String razorpay_signature;
}
