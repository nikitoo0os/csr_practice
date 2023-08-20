package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.dto.api.ReportDataDTO;
import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.entity.api.ReportData;
import com.vyatsu.practiceCSR.exception.AppException;
import com.vyatsu.practiceCSR.mapper.ReportDataMapper;
import com.vyatsu.practiceCSR.mapper.ReportMapper;
import com.vyatsu.practiceCSR.service.api.ReportDataService;
import com.vyatsu.practiceCSR.service.api.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report/data")
public class ReportDataController {

    private final ReportDataService reportDataService;
    private final ReportService reportService;
    private final ReportDataMapper reportDataMapper;
    private final ReportMapper reportMapper;

    @GetMapping("/services/{reportId}")
    public ResponseEntity<List<ReportDataDTO>> getReportDataByReportId(@PathVariable Long reportId){
        List<ReportData> reportData = reportDataService.getReportDataByReportId(reportId);
        List<ReportDataDTO> reportDataDTOs = reportDataMapper.toReportDataDTO(reportData);
        return ResponseEntity.ok(reportDataDTOs);
    }

    @PutMapping
    public ResponseEntity<Void> updateReportData(@RequestBody List<ReportDataDTO> reportDataDTO){
        List<ReportData> reportData = reportDataMapper.toReportData(reportDataDTO);
        reportDataService.saveReportData(reportData);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/copy/{reportId}")
    public ResponseEntity<ReportDTO> copyReportData(@PathVariable Long reportIdTo, @RequestBody Long reportIdFrom){
        Report reportFrom = reportService.getReportById(reportIdFrom);

        boolean reportIsLastMonth = reportService.isLastMonth(reportFrom);
        Report cloneReport = null;
        if(reportIsLastMonth){
            cloneReport = reportDataService.createReportFromPrevious(reportFrom);
        }
        return ResponseEntity.ok(reportMapper.toReportDTO(cloneReport));
    }


}
