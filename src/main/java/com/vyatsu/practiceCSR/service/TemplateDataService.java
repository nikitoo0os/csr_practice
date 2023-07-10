package com.vyatsu.practiceCSR.service;

import com.vyatsu.practiceCSR.entity.api.TemplateData;

public interface TemplateDataService {

    void createTemplateData(TemplateData templateData);

    TemplateData getTemplateDataByTemplateId(Long id);
}
