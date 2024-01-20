package com.marvellous.paymentsystem.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class UtilityHelper {
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static JSONObject getJsonObject(Object obj) throws JsonProcessingException {
        String jsonString = objectMapper.writeValueAsString(obj);
        return new JSONObject(jsonString);
    }

    public static Integer convertToCent(Float amount) {
        return (int)(amount*100);
    }
}
