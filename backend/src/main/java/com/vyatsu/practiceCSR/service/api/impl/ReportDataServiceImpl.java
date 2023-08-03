package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.entity.api.ReportData;
import com.vyatsu.practiceCSR.repository.ReportDataRepository;
import com.vyatsu.practiceCSR.repository.ReportRepository;
import com.vyatsu.practiceCSR.service.api.ReportDataService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportDataServiceImpl implements ReportDataService {
    private final ReportDataRepository reportDataRepository;
    private final ReportRepository reportRepository;

    @Override
    public List<ReportData> getReportDataByReportId(Long id) {
        return reportDataRepository.findByReportId(id);
    }

    @Override
    public void saveReportData(List<ReportData> reportData) {
        reportDataRepository.saveAll(reportData);
    }

    @Override
    public Report createReportFromPrevious(Report fromReport) {
        Report cloneReport = reportRepository.save(new Report());
        List<ReportData> fromReportData = reportDataRepository.findByReportId(Long.valueOf(fromReport.getId()));
        for (ReportData reportData : fromReportData) {
            reportData.setReport(cloneReport);
        }
        reportDataRepository.saveAll(fromReportData);
        return cloneReport;
    }

}
