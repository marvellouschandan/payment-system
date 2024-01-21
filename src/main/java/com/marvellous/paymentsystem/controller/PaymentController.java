package com.marvellous.paymentsystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvellous.paymentsystem.models.CreateOrderReq;
import com.marvellous.paymentsystem.models.SequenceGenerator;
import com.marvellous.paymentsystem.models.enums.SequenceName;
import com.marvellous.paymentsystem.models.rzp.request.RzpCreateOrderRequest;
import com.marvellous.paymentsystem.models.rzp.response.RzpCapturePaymentResponse;
import com.marvellous.paymentsystem.service.SequenceGeneratorService;
import com.marvellous.paymentsystem.utils.UtilityHelper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class PaymentController {
    RazorpayClient razorpayClient;
    ObjectMapper objectMapper;
    SequenceGeneratorService sequenceGeneratorService;
    String razorpayKeySecret;

    PaymentController(RazorpayClient razorpayClient, ObjectMapper objectMapper, SequenceGeneratorService sequenceGeneratorService, @Value("${razorpay.key.secret}") String razorpayKeySecret) {
        this.razorpayClient = razorpayClient;
        this.objectMapper = objectMapper;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.razorpayKeySecret = razorpayKeySecret;
    }

    @PostMapping("/checkout")
    public ResponseEntity checkout(@RequestBody CreateOrderReq createOrderReq) throws JsonProcessingException, RazorpayException {
        String receipt = String.valueOf(sequenceGeneratorService.getNextReceipt());
        RzpCreateOrderRequest orderRequest = RzpCreateOrderRequest.builder()
                        .amount(UtilityHelper.convertToCent(createOrderReq.getAmount()))
                        .currency(createOrderReq.getCurrency())
                        .receipt(receipt)
                        .notes(createOrderReq.getNotes())
                        .build();
        JSONObject createOrderJson = UtilityHelper.getJsonObject(orderRequest);
        Order order = razorpayClient.orders.create(createOrderJson);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(order.toJson().toMap());
    }

    @PostMapping("/confirm")
    public ResponseEntity processCallback(@RequestBody RzpCapturePaymentResponse callbackResponse) throws JsonProcessingException, RazorpayException {
        JSONObject callbackJson = UtilityHelper.getJsonObject(callbackResponse);

        if (!Utils.verifyPaymentSignature(callbackJson, razorpayKeySecret)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Received");
    }
}
