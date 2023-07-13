package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
