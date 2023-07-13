package com.vyatsu.practiceCSR.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRateString = "#{@schedulerProvider.getFrequency()}")
    public void reportCurrentTime() {
        log.info("Текущее время {}", dateFormat.format(new Date()));
    }

//    @Scheduled(fixedDelayString = "#{report.getFrequency()}")
//    public Report createFrequencyReport(Report report){
//        log.info("Создан периодичный отчет id:" + report.getId());
//        if(report.getFrequency() != null){
//           // return schedulerProvider.extensionReport(report);
//        }
//        return null;
//    }
}
