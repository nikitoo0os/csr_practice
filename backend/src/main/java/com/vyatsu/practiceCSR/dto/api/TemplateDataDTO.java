package com.vyatsu.practiceCSR.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemplateDataDTO {
    private Integer id;
    private TemplateDTO template;
    private ServiceDTO service;
}
