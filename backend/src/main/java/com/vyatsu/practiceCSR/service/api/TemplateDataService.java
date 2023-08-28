package com.vyatsu.practiceCSR.service.api;

import com.vyatsu.practiceCSR.dto.api.TemplateDataDTO;
import com.vyatsu.practiceCSR.entity.api.TemplateData;

import java.util.List;

public interface TemplateDataService {

    void createTemplateData(TemplateData templateData);

    List<TemplateDataDTO> getTemplateDataByTemplateId(Long id);

    void deleteAllTemplateDataByTemplateId(String token, Long id);
}
