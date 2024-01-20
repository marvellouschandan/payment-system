package com.marvellous.paymentsystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvellous.paymentsystem.models.CreateOrderReq;
import com.marvellous.paymentsystem.models.SequenceGenerator;
import com.marvellous.paymentsystem.models.enums.SequenceName;
import com.marvellous.paymentsystem.models.rzp.request.RzpCreateOrderRequest;
import com.marvellous.paymentsystem.service.SequenceGeneratorService;
import com.marvellous.paymentsystem.utils.UtilityHelper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    RazorpayClient razorpayClient;
    ObjectMapper objectMapper;
    SequenceGeneratorService sequenceGeneratorService;

    PaymentController(RazorpayClient razorpayClient, ObjectMapper objectMapper, SequenceGeneratorService sequenceGeneratorService) {
        this.razorpayClient = razorpayClient;
        this.objectMapper = objectMapper;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @PostMapping("/checkout")
    public ResponseEntity checkout(@RequestBody CreateOrderReq createOrderReq) throws JsonProcessingException, RazorpayException {
        String receipt = String.valueOf(sequenceGeneratorService.getNextSequence(SequenceName.RECEIPT));
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
}
