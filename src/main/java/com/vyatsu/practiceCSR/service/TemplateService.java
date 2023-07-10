package com.vyatsu.practiceCSR.service;

import com.vyatsu.practiceCSR.dto.api.TemplateDTO;
import com.vyatsu.practiceCSR.entity.api.Template;
import org.springframework.http.ResponseEntity;

public interface TemplateService {

    void deleteById(Long id);

    void createTemplate();

    TemplateDTO getTemplateById(Long id);

}
