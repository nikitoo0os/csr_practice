package com.vyatsu.practiceCSR.service.impl;

import com.vyatsu.practiceCSR.dto.api.TemplateDTO;
import com.vyatsu.practiceCSR.entity.api.Template;
import com.vyatsu.practiceCSR.mapper.TemplateMapper;
import com.vyatsu.practiceCSR.repository.TemplateRepository;
import com.vyatsu.practiceCSR.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    private final TemplateMapper templateMapper;
    @Override
    public void deleteById(Long id) {
        templateRepository.deleteById(id);
    }

    @Override
    public TemplateDTO createTemplate(Template template) {
        Template template1 = new Template();
        template1.setName(template.getName());
        template1.setDate(new Timestamp(System.currentTimeMillis()));
        template1 = templateRepository.save(template1);
        TemplateDTO templateDTO = templateMapper.toTemplateDTO(template1);
        return templateDTO;
    }

    @Override
    public TemplateDTO getTemplateById(Long id) {
        Template template = templateRepository.findById(id).get();
        return templateMapper.toTemplateDTO(template);
    }

    @Override
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }
}
