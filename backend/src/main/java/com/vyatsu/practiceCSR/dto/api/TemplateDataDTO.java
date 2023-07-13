package com.vyatsu.practiceCSR.dto.api;

import com.vyatsu.practiceCSR.entity.api.Service;
import com.vyatsu.practiceCSR.entity.api.Template;
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
    private Template template;
    private Service service;
}
