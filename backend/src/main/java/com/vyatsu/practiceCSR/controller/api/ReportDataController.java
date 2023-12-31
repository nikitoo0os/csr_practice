package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.dto.api.ReportDataDTO;
import com.vyatsu.practiceCSR.dto.helper.CopyReportDTO;
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
    public ResponseEntity<Void> updateReportData(@RequestHeader("Authorization") String token, @RequestBody List<ReportDataDTO> reportDataDTO){
        List<ReportData> reportData = reportDataMapper.toReportData(reportDataDTO);
        reportDataService.saveReportData(token, reportData);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/copy/{reportIdTo}")
    public ResponseEntity<Void> copyReportData(@PathVariable Long reportIdTo, @RequestBody String id){
        Report reportFrom = reportService.getReportById(Long.valueOf(id));
        Report reportTo = reportService.getReportById(reportIdTo);

        boolean reportIsLastMonth = reportService.isLastMonth(reportFrom);
        reportDataService.createReportFromPrevious(reportFrom, reportTo);

        return ResponseEntity.ok().build();
    }


}
