package com.vyatsu.practiceCSR.service.scheduling;

import com.vyatsu.practiceCSR.bean.TaskDefinitionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobSchedulingService {

    @Autowired
    private TaskSchedulingService taskSchedulingService;

    @Autowired
    private TaskDefinitionBean taskDefinitionBean;
//    public void scheduleATask(TaskDefinition taskDefinition, Integer reportId) {
//        taskDefinitionBean.setTaskDefinition(taskDefinition);
//        taskSchedulingService.scheduleATask(reportId, taskDefinitionBean, taskDefinition.getCronExpression());
//    }
}
