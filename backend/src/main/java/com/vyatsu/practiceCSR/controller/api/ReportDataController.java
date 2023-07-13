package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api.ReportDataDTO;
import com.vyatsu.practiceCSR.entity.api.ReportData;
import com.vyatsu.practiceCSR.mapper.ReportDataMapper;
import com.vyatsu.practiceCSR.mapper.ReportMapper;
import com.vyatsu.practiceCSR.service.api.ReportDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report/data")
public class ReportDataController {

    private final ReportDataService reportDataService;
    private final ReportDataMapper reportDataMapper;

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
}
