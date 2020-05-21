package com.miwtech.gildedrose.task.framework.exceptions;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class TaskException extends RuntimeException {
    public TaskException() {
        super();
    }
    @Getter
    @Setter
    private String endState;
    public TaskException(final String msg) {
        super(msg);
    }
    public TaskException(final String msg, final String endState) {
        super(msg);
        this.endState = endState;
    }
}
