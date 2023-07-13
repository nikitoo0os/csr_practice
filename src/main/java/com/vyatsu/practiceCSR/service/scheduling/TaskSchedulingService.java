package com.vyatsu.practiceCSR.service.scheduling;

import com.vyatsu.practiceCSR.entity.api.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class TaskSchedulingService {

    @Autowired
    private TaskScheduler taskScheduler;

    Map<Integer, ScheduledFuture<?>> jobsMap = new HashMap<>();

    public void scheduleATask(Report report, Runnable tasklet) {
        System.out.println("Задача по расписанию, отчет №: " + report.getId() + " с частотой: " + report.getFrequency() + "мс");
//        PeriodicTrigger periodicTrigger = new PeriodicTrigger(report.getFrequency(), TimeUnit.MILLISECONDS);
        PeriodicTrigger periodicTrigger = new PeriodicTrigger(5, TimeUnit.SECONDS);
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(tasklet, periodicTrigger);
        jobsMap.put(report.getId(), scheduledTask);
    }

    public void removeScheduledTask(Integer reportId) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(reportId);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(reportId, null);
        }
    }
}