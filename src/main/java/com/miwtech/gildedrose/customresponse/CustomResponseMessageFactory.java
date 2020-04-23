package com.miwtech.gildedrose.customresponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomResponseMessageFactory {
    public CustomResponseMessage getSuccessfulPurchaseResponse() {
        CustomResponseMessage customResponseMessage = new CustomResponseMessage("OK", "Purchase complete.");
        customResponseMessage.setStatus((HttpStatus.OK.value()));
        return customResponseMessage;
    }
}
