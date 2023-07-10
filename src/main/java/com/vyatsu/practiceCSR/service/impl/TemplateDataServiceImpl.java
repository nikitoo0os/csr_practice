package com.vyatsu.practiceCSR.service.impl;

import com.vyatsu.practiceCSR.entity.api.TemplateData;
import com.vyatsu.practiceCSR.repository.TemplateDataRepository;
import com.vyatsu.practiceCSR.service.TemplateDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TemplateDataServiceImpl implements TemplateDataService {
    private final TemplateDataRepository templateDataRepository;
    @Override
    public void createTemplateData(TemplateData templateData) {
        templateDataRepository.save(templateData);
    }

    @Override
    public TemplateData getTemplateDataByTemplateId(Long id) {
        return templateDataRepository.findByTemplateId(id);
    }
}
