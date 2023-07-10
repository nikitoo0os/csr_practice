package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api.TemplateDataDTO;
import com.vyatsu.practiceCSR.entity.api.TemplateData;
import com.vyatsu.practiceCSR.mapper.TemplateDataMapper;
import com.vyatsu.practiceCSR.service.TemplateDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/template/data")
public class TemplateDataController {
    private final TemplateDataService templateDataService;
    private final TemplateDataMapper templateDataMapper;
    @PostMapping("/create")
    public ResponseEntity<Void> createTemplateData(@RequestBody TemplateDataDTO templateDataDTO) {
        if (templateDataDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        TemplateData templateData = templateDataMapper.toTemplateData(templateDataDTO);
        templateDataService.createTemplateData(templateData);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<TemplateData> getTemplateDataByTemplateId(@PathVariable Long id){
        TemplateData templateData = templateDataService.getTemplateDataByTemplateId(id);
        return ResponseEntity.ok(templateData);
    }



}
