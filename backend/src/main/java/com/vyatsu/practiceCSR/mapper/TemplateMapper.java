package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.TemplateDTO;
import com.vyatsu.practiceCSR.entity.api.Template;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    TemplateDTO toTemplateDTO(Template template);

    List<TemplateDTO> toTemplateDTOList(List<Template> templates);

    Template toTemplate(TemplateDTO templateDTO);
}
