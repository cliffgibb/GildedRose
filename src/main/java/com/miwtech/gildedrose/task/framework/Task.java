package com.miwtech.gildedrose.task.framework;

public interface Task {
    TaskExecutionResult execute(final TaskContext taskContext);
}
