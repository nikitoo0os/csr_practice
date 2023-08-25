package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.config.auth.UserAuthenticationProvider;
import com.vyatsu.practiceCSR.dto.api.TemplateDTO;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.entity.api.Template;
import com.vyatsu.practiceCSR.mapper.TemplateMapper;
import com.vyatsu.practiceCSR.service.api.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/template")
public class TemplateController {

    private final TemplateService templateService;
    private final TemplateMapper templateMapper;
    private final UserAuthenticationProvider authenticationProvider;

    @PostMapping
    public ResponseEntity<TemplateDTO> createTemplate(@RequestHeader("Authorization") String token, @RequestBody TemplateDTO templateDTO){
        Template template = templateMapper.toTemplate(templateDTO);
        TemplateDTO templateDTO1 = templateService.createTemplate(token, template);
        return ResponseEntity.ok(templateDTO1);
    }

    @GetMapping
    public ResponseEntity<List<TemplateDTO>> getAllTemplates(){
        List<Template> templates = templateService.getAllTemplates();
        List<TemplateDTO> templateDTOs = templateMapper.toTemplateDTOList(templates);
        return ResponseEntity.ok(templateDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateDTO> getTemplateById(@PathVariable Long id){
        return ResponseEntity.ok(templateService.getTemplateById(id));
    }
}
