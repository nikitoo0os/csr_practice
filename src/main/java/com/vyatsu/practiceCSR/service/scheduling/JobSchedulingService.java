package com.vyatsu.practiceCSR.service.scheduling;

import com.vyatsu.practiceCSR.bean.TaskDefinitionBean;
import com.vyatsu.practiceCSR.dto.task.TaskDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobSchedulingService {

    @Autowired
    private TaskSchedulingService taskSchedulingService;

    @Autowired
    private TaskDefinitionBean taskDefinitionBean;
    public void scheduleATask(TaskDefinition taskDefinition, Long reportId) {
        taskDefinitionBean.setTaskDefinition(taskDefinition);
        taskSchedulingService.scheduleATask(UUID.randomUUID(), taskDefinitionBean, taskDefinition.getCronExpression());
    }
}
