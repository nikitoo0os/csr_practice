package com.vyatsu.practiceCSR.provider;

import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.service.api.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SchedulerProvider {
    private final ReportService reportService;
    public String getFrequency(){
        return "5000";
    }

    public Report extensionReport(Report report){
        return reportService.extensionReport(report);
    }
}
