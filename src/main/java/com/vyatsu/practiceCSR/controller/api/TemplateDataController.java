package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.entity.api.TemplateData;
import com.vyatsu.practiceCSR.repository.TemplateDataRepository;
import com.vyatsu.practiceCSR.service.TemplateDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/template/data")
public class TemplateDataController {
    private final TemplateDataService templateDataService;
    @PostMapping("/create")
    public ResponseEntity<Void> createTemplateData(@RequestBody TemplateData templateData) {
        if (templateData == null) {
            return ResponseEntity.badRequest().build();
        }
        templateDataService.createTemplateData(templateData);
        return ResponseEntity.ok().build();
    }
}
