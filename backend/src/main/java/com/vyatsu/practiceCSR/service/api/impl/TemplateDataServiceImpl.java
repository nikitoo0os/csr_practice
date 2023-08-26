package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.config.auth.UserAuthenticationProvider;
import com.vyatsu.practiceCSR.dto.api.TemplateDataDTO;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.entity.api.TemplateData;
import com.vyatsu.practiceCSR.logger.LoggerCSR;
import com.vyatsu.practiceCSR.mapper.ServiceMapper;
import com.vyatsu.practiceCSR.mapper.TemplateDataMapper;
import com.vyatsu.practiceCSR.mapper.TemplateMapper;
import com.vyatsu.practiceCSR.repository.TemplateDataRepository;
import com.vyatsu.practiceCSR.service.api.TemplateDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TemplateDataServiceImpl implements TemplateDataService {
    private final TemplateDataRepository templateDataRepository;
    private final TemplateDataMapper templateDataMapper;
    private final TemplateMapper templateMapper;
    private final ServiceMapper serviceMapper;
    @Override
    public void createTemplateData(TemplateData templateData) {
        templateDataRepository.save(templateData);
    }

    @Override
    public List<TemplateDataDTO> getTemplateDataByTemplateId(Long id) {
        List<TemplateData> templateDataList = templateDataRepository.findListByTemplateId(id);
        List<TemplateDataDTO> templateDataDTOs = new ArrayList<>();
        for(TemplateData templateData : templateDataList){
            TemplateDataDTO templateDataDTO = new TemplateDataDTO();
            templateDataDTO.setTemplate(templateMapper.toTemplateDTO(templateData.getTemplate()));
            templateDataDTO.setService(serviceMapper.serviceToServiceDTO(templateData.getService()));
            templateDataDTO.setId(templateData.getId());
            templateDataDTOs.add(templateDataDTO);
        }
        return templateDataDTOs;
    }
}
