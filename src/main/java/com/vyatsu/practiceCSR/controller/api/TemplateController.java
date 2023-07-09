package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/templates")
public class TemplateController {

    private final TemplateService templateService;

    //empty
}
