package com.vyatsu.practiceCSR.service.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Service
public class TaskSchedulingService {

    @Autowired
    private TaskScheduler taskScheduler;

    Map<UUID, ScheduledFuture<?>> jobsMap = new HashMap<>();

    public void scheduleATask(UUID uuid, Runnable tasklet, String cronExpression) {
        System.out.println("Задача по расписанию: " + uuid + " cron: " + cronExpression);
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(tasklet, new CronTrigger(cronExpression, TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        jobsMap.put(uuid, scheduledTask);
    }

    public void removeScheduledTask(UUID uuid) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(uuid);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(uuid, null);
        }
    }
}