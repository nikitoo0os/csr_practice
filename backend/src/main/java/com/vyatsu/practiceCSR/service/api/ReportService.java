package com.vyatsu.practiceCSR.service.api;

import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.entity.api.Report;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    Report createReport(ReportDTO reportDTO);

    Report getReportById(Long id);

    List<Report> getActiveReportByUserId(Long userId);

    List<Report> getInactiveReportByUserId(Long userId);

    void createReportsUser(Long reportId, List<Integer> userDTOs);

    void updateStatusToEnd(Long reportId);

    HttpEntity<ByteArrayResource> getResultReportData(LocalDate localDateFrom, LocalDate localDateTo, Long templateId) throws IOException;

    boolean isLastMonth(Report reportFrom);
}
