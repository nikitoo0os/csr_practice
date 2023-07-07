package com.vyatsu.practiceCSR.provider;

import org.springframework.stereotype.Component;

@Component
public class SchedulerProvider {
    public String getFrequency(){
        return "5000";
    }
}
