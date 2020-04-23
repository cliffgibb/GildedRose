package com.miwtech.gildedrose.customresponse;

import lombok.Data;

@Data
public class CustomResponseMessage {
    private String statusCode;
    private String message;
    private int status;

    public CustomResponseMessage(String statusCode, String message) {
        super();
        this.statusCode = statusCode;
        this.message = message;
    }
}
