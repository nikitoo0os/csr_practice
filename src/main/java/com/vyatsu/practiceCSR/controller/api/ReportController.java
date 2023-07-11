package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.mapper.ReportMapper;
import com.vyatsu.practiceCSR.service.api.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private final ReportMapper reportMapper;
    @PostMapping
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO){
        Report report = reportService.createReport(reportDTO);
        return ResponseEntity.ok(reportMapper.toReportDTO(report));
    }
}
