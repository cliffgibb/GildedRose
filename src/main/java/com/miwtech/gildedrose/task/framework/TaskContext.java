package com.miwtech.gildedrose.task.framework;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
public class TaskContext {
    @Getter
    private String deviceId;
}
