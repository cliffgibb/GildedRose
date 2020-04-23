package com.miwtech.gildedrose.exception;

import com.miwtech.gildedrose.customresponse.CustomResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(value = InvalidItemPageException.class)
    public ResponseEntity<CustomResponseMessage> handleInvalidItemPageException(InvalidItemPageException e) {
        CustomResponseMessage error = new CustomResponseMessage("NOT_FOUND_ERROR", e.getMessage());
        error.setStatus((HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = InvalidItemException.class)
    public ResponseEntity<CustomResponseMessage> handleInvalidItemException(InvalidItemException e) {
        CustomResponseMessage error = new CustomResponseMessage("BAD_REQUEST", e.getMessage());
        error.setStatus((HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = ItemNotAvailableException.class)
    public ResponseEntity<CustomResponseMessage> handleItemNotAvailableException(ItemNotAvailableException e) {
        CustomResponseMessage error = new CustomResponseMessage("BAD_REQUEST", e.getMessage());
        error.setStatus((HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<CustomResponseMessage> handleUsernameNotFoundException(UsernameNotFoundException e) {
        CustomResponseMessage error = new CustomResponseMessage("BAD_REQUEST", e.getMessage());
        error.setStatus((HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
