package com.miwtech.gildedrose.task.tasks.playstore.login;

import com.miwtech.gildedrose.task.framework.BaseTask;
import com.miwtech.gildedrose.task.framework.TaskContext;
import com.miwtech.gildedrose.task.framework.TaskExecutionResult;
import com.miwtech.gildedrose.task.framework.TaskStatus;
import com.miwtech.gildedrose.task.framework.exceptions.TaskException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayStoreLoginTask extends BaseTask {
    @Override
    public TaskExecutionResult execute(final TaskContext taskContext) {
        /*
        try {
            if (true) {
                throw new TaskException("Play store login FAILED");
            }

        } catch (Exception e) {
            log.debug("This is an exception!");
            TaskExecutionResult taskExecutionResult = new TaskExecutionResult();
            taskExecutionResult.setStatus(TaskStatus.FAILURE);
            taskExecutionResult.setMessage(e.getMessage());

            return taskExecutionResult;
        }

         */
        if (true) {
            throw new TaskException("Play store login FAILED", "HOME");
        }
        TaskExecutionResult taskExecutionResult = new TaskExecutionResult();
        taskExecutionResult.setStatus(TaskStatus.SUCCESS);
        taskExecutionResult.setMessage("Play store login complete");

        return taskExecutionResult;
    }

    public static TaskExecutionResult fulfill(String deviceId) {
        return fulfill(deviceId, new PlayStoreLoginTask());
    }

    /*
    @ExceptionHandler({ TaskException.class })
    public TaskExecutionResult handleException(TaskException e) {
        TaskExecutionResult taskExecutionResult = new TaskExecutionResult();
        taskExecutionResult.setStatus(TaskStatus.FAILURE);
        taskExecutionResult.setMessage(e.getMessage());
        return taskExecutionResult;
    }

     */

}
