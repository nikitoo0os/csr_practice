package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.TemplateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TemplateDataRepository extends JpaRepository<TemplateData, Long> {
    @Query("SELECT td FROM TemplateData td WHERE td.id = ?1")
    TemplateData findByTemplateId(Long id);

    @Query("SELECT td FROM TemplateData td WHERE td.id = ?1")
    List<TemplateData> findListByTemplateId(Long id);

}
