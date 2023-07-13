package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.entity.api.ReportData;
import com.vyatsu.practiceCSR.repository.ReportDataRepository;
import com.vyatsu.practiceCSR.service.api.ReportDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportDataServiceImpl implements ReportDataService {
    private final ReportDataRepository reportDataRepository;
    @Override
    public List<ReportData> getReportDataByReportId(Long id) {
        List<ReportData> reportData = reportDataRepository.findByReportId(id);
        return reportData;
    }

    @Override
    public void saveReportData(List<ReportData> reportData) {
        reportDataRepository.saveAll(reportData);
    }
}
