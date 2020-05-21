package com.miwtech.gildedrose.task.framework;

import lombok.Data;

@Data
public class TaskExecutionResult {
    private TaskStatus status;
    private String message;
}
