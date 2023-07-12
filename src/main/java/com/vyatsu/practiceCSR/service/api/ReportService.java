package com.vyatsu.practiceCSR.service.api;

import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.entity.api.Report;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface ReportService {
    Report createReport(ReportDTO reportDTO);

    Report extensionReport(Report report);

    Report getReportById(Long id);

    List<Report> getActiveReportByUserId(Long userId);

    List<Report> getInactiveReportByUserId(Long userId);
}
