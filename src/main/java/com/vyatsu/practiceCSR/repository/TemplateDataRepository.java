package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.TemplateData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateDataRepository extends JpaRepository<TemplateData, Long> {
}
