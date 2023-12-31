package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.TemplateData;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TemplateDataRepository extends JpaRepository<TemplateData, Long> {
    @Query("SELECT td FROM TemplateData td WHERE td.id = ?1")
    TemplateData findByTemplateId(Long id);

    @Query("SELECT td FROM TemplateData td WHERE td.template.id = ?1")
    List<TemplateData> findListByTemplateId(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM TemplateData td WHERE td.template.id = ?1")
    void dropAllTemplateDataByTemplateId(Long id);

}
