package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.config.auth.UserAuthenticationProvider;
import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.dto.api.UserDTO;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.mapper.ReportMapper;
import com.vyatsu.practiceCSR.service.api.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private final UserAuthenticationProvider authenticationProvider;
    private final ReportMapper reportMapper;

    @GetMapping("/active/user")
    public ResponseEntity<List<ReportDTO>> getActiveReportByUser(@RequestHeader("Authorization") String token){
        try {
            // Извлекаем токен из заголовка Authorization
            String jwtToken = token.substring(7); // Предполагается, что токен имеет формат "Bearer <токен>"

            // Проверяем токен и получаем аутентифицированного клиента
            Authentication authentication = authenticationProvider.validateToken(jwtToken);

            // Получаем ID аутентифицированного клиента
            Long userId = ((UserAuthDto) authentication.getPrincipal()).getId();

            // Получаем профили для аутентифицированного клиента
            List<Report> reports = reportService.getActiveReportByUserId(userId);
            List<ReportDTO> reportDTOs = reportMapper.toListReportDTO(reports);
            return ResponseEntity.ok(reportDTOs);
        } catch (Exception e) {
            // Проверка токена не удалась или произошла другая ошибка
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/inactive/user")
    public ResponseEntity<List<ReportDTO>> getInactiveReportByUser(@RequestHeader("Authorization") String token){
        try {
            // Извлекаем токен из заголовка Authorization
            String jwtToken = token.substring(7); // Предполагается, что токен имеет формат "Bearer <токен>"

            // Проверяем токен и получаем аутентифицированного клиента
            Authentication authentication = authenticationProvider.validateToken(jwtToken);

            // Получаем ID аутентифицированного клиента
            Long userId = ((UserAuthDto) authentication.getPrincipal()).getId();

            // Получаем профили для аутентифицированного клиента
            List<Report> reports = reportService.getInactiveReportByUserId(userId);
            List<ReportDTO> reportDTOs = reportMapper.toListReportDTO(reports);
            return new ResponseEntity<>(reportDTOs, HttpStatus.OK);
        } catch (Exception e) {
            // Проверка токена не удалась или произошла другая ошибка
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/inactive/user/{id}")
    public ResponseEntity<List<ReportDTO>> getInactiveReportsByUserId(@PathVariable Long id){
        List<Report> inactiveReports = reportService.getInactiveReportByUserId(id);
        List<ReportDTO> inactiveReportsDTO = reportMapper.toListReportDTO(inactiveReports);
        return ResponseEntity.ok(inactiveReportsDTO);
    }

    @GetMapping("/active/user/{id}")
    public ResponseEntity<List<ReportDTO>> getActiveReportsByUserId(@PathVariable Long id){
        List<Report> activeReports = reportService.getActiveReportByUserId(id);
        List<ReportDTO> activeReportsDTO = reportMapper.toListReportDTO(activeReports);
        return ResponseEntity.ok(activeReportsDTO);
    }

    @PostMapping
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO){
        Report report = reportService.createReport(reportDTO);
        return ResponseEntity.ok(reportMapper.toReportDTO(report));
    }

    @PostMapping("/user/{reportId}")
    public ResponseEntity<Void> createReportsUser(@PathVariable Long reportId, @RequestBody List<Integer> usersId){
        reportService.createReportsUser(reportId, usersId);
        return ResponseEntity.ok().build();
    }

}
