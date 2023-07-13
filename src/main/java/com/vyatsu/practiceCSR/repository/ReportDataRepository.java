package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.ReportData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportDataRepository extends JpaRepository<ReportData, Long> {
    @Query("SELECT rd FROM ReportData rd WHERE rd.report.id = ?1")
    List<ReportData> findByReportId(Long id);
}
