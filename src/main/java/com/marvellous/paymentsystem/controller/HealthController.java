package com.marvellous.paymentsystem.controller;

import com.marvellous.paymentsystem.models.ServerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity health() {
        ServerResponse response = ServerResponse.builder()
                .success(Boolean.TRUE)
                .data("Server is healthy!")
                .statusCode(HttpStatus.OK.value())
                .error(Collections.emptyList())
                .build();
        return ResponseEntity.ok(response);
    }
}
