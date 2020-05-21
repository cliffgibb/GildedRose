package com.miwtech.gildedrose.task.framework;

import com.miwtech.gildedrose.task.framework.exceptions.TaskException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public abstract class BaseTask implements Task {
    protected static TaskExecutionResult fulfill(final String deviceId, final BaseTask task) {
        TaskContext taskContext = TaskContext.builder().deviceId(deviceId).build();
        /*
        TaskExecutionResult taskExecutionResult = null;

        try {
            taskExecutionResult = task.execute(taskContext);
        } catch (TaskException e) {
            log.error(" There was a problem... " + e.getMessage() + " The end state was... [" + e.getEndState() + "]");
        }

        return taskExecutionResult;
       */

        final TaskExecutionResult taskExecutionResult = task.execute(taskContext);
        return taskExecutionResult;
    }
}
