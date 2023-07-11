package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
