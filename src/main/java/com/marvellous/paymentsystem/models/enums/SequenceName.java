package com.marvellous.paymentsystem.models.enums;

public enum SequenceName {
    RECEIPT("RECEIPT");

    private final String value;

    SequenceName(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
