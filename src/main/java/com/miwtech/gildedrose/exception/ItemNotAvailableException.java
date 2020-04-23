package com.miwtech.gildedrose.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemNotAvailableException extends Exception {
    public ItemNotAvailableException(String errorMessage) {
        super(errorMessage);
    }
}
