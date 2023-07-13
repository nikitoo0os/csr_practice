package com.vyatsu.practiceCSR.dto.task;

import lombok.Data;

@Data
public class TaskDefinition {
    private String cronExpression;
    private String actionType;
    private String data;
}
