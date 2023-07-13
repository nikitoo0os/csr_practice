package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {
    List<UserReport> findByUserId(Long userId);
}
