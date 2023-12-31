package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM Report r WHERE r.isActive = true AND r.region.id = ?1")
    List<Report> findActiveReportsByRegionId(Long id);
    List<Report> findByTemplateId(Long id);
    @Query("SELECT r FROM Report r WHERE (r.isActive = false AND r.region.id = ?1)")
    List<Report> findInactiveReportByRegionId(Long id);
    Report findByRegionId(Long id);
    @Query("SELECT r FROM Report r WHERE (r.isActive = false AND r.template.id = ?1)")
    List<Report> findCompletedReportsByTemplateId(Long id);
}
