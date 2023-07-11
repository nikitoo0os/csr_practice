package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.mapper.ReportMapper;
import com.vyatsu.practiceCSR.repository.ReportRepository;
import com.vyatsu.practiceCSR.service.api.RegionService;
import com.vyatsu.practiceCSR.service.api.ReportService;
import com.vyatsu.practiceCSR.service.scheduling.TaskSchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final TaskSchedulingService taskSchedulingService;
    private final RegionService regionService;
    @Override
    public Report createReport(ReportDTO reportDTO) {
        Report report = reportMapper.toReport(reportDTO);
        report.setIsActive(true);
        report.setIsCompleted(false);
        report.setRegion(regionService.getRegionById(reportDTO.getRegionId()));
        if (report.getFrequency() != null) {
            return extensionReport(report);
        } else {
            return reportRepository.save(report);
        }
    }

    private Report report1;
    @Override
    public Report extensionReport(Report report) {

        taskSchedulingService.scheduleATask(
                UUID.randomUUID(),
                () -> report1 = reportRun(report),
                String.valueOf(report.getFrequency())
        );
        return report1;
    }

    public Report reportRun(Report report) {
        if(!report.getIsActive()) {
            //дата окончания старого отчета + дни активности отчета
            LocalDate endDate = report.getEndDate().plusDays(report.getActiveDays());

            LocalDate currentDateTime = LocalDate.now();

            report.setId(null);
            report.setStartDate(currentDateTime);
            report.setEndDate(endDate);
            report.setIsCompleted(false);
            report.setIsActive(true);

            report.setIsCompleted(true);
            reportRepository.save(report);

            //записываем новый(продленный старый) отчет
            return reportRepository.save(report);
        }
        return report;
    }

    @Override
    public Report getReportById(Long id) {
        Report report = reportRepository.findById(id).get();
        LocalDate currentDate = LocalDate.now();
        boolean currentDateIsAfterEndDate = currentDate.isAfter(report.getEndDate());
        if(currentDateIsAfterEndDate){
            report.setIsActive(false);
        }
        return report;
    }

}
