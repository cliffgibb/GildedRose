package com.miwtech.gildedrose.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidItemPageException extends Exception {
    public InvalidItemPageException(String errorMessage) {
        super(errorMessage);
    }
}
