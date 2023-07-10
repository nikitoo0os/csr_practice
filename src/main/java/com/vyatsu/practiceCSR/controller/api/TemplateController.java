package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/template")
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping
    public ResponseEntity<Void> createTemplate(){
        templateService.createTemplate();
        return ResponseEntity.ok().build();
    }
}
