package com.miwtech.gildedrose.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidItemException extends Exception {
    public InvalidItemException(String errorMessage) {
        super(errorMessage);
    }
}
