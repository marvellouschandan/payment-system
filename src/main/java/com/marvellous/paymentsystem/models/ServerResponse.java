package com.marvellous.paymentsystem.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ServerResponse {
    @Data
    @Builder
    static class ServerError {
        Integer code;
        String description;
    }

    Boolean success;
    Integer statusCode;
    Object data;
    List<ServerError> error;
}
