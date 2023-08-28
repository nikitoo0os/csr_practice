package com.vyatsu.practiceCSR.service.api;

import com.vyatsu.practiceCSR.dto.api.TemplateDTO;
import com.vyatsu.practiceCSR.entity.api.Template;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TemplateService {

    void deleteById(Long id);

    TemplateDTO createTemplate(String token, Template template);

    TemplateDTO getTemplateById(Long id);

    List<Template> getAllTemplates();

    Template updateTemplate(String token, Template template);
}
