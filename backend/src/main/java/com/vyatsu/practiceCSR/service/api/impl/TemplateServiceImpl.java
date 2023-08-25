package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.config.auth.UserAuthenticationProvider;
import com.vyatsu.practiceCSR.dto.api.TemplateDTO;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.entity.api.Template;
import com.vyatsu.practiceCSR.logger.EnumWarnLog;
import com.vyatsu.practiceCSR.logger.LoggerCSR;
import com.vyatsu.practiceCSR.mapper.TemplateMapper;
import com.vyatsu.practiceCSR.repository.TemplateRepository;
import com.vyatsu.practiceCSR.service.api.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    private final TemplateMapper templateMapper;
    private final UserAuthenticationProvider authenticationProvider;
    @Override
    public void deleteById(Long id) {
        templateRepository.deleteById(id);
    }

    @Override
    public TemplateDTO createTemplate(String token, Template template) {
        String jwtToken = token.substring(7);
        Authentication authentication = authenticationProvider.validateToken(jwtToken);
        Long userId = ((UserAuthDto) authentication.getPrincipal()).getId();

        Template template1 = template;
        template1.setDate(new Timestamp(System.currentTimeMillis()));
        template1 = templateRepository.save(template1);
        TemplateDTO templateDTO = templateMapper.toTemplateDTO(template1);

        LoggerCSR.createWarnMsg(EnumWarnLog.CREATE_TEMPLATE, userId, Long.valueOf(template1.getId()));
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
