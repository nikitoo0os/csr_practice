package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api.TemplateDTO;
import com.vyatsu.practiceCSR.entity.api.Template;
import com.vyatsu.practiceCSR.mapper.TemplateMapper;
import com.vyatsu.practiceCSR.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/template")
public class TemplateController {

    private final TemplateService templateService;
    private final TemplateMapper templateMapper;

    @PostMapping
    public ResponseEntity<TemplateDTO> createTemplate(){
        templateService.createTemplate();
        return ResponseEntity.ok().build();
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
