package com.vyatsu.practiceCSR.bean;

import com.vyatsu.practiceCSR.dto.task.TaskDefinition;
import org.springframework.stereotype.Service;

@Service
public class TaskDefinitionBean implements Runnable {

    private TaskDefinition taskDefinition;

    @Override
    public void run() {
        System.out.println("Запущено действие: " + taskDefinition.getActionType());
        System.out.println("С данными: " + taskDefinition.getData());
    }

    public TaskDefinition getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(TaskDefinition taskDefinition) {
        this.taskDefinition = taskDefinition;
    }
}
