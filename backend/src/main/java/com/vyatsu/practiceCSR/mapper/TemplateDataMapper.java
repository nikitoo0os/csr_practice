package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.TemplateDataDTO;
import com.vyatsu.practiceCSR.entity.api.TemplateData;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TemplateDataMapper {

    TemplateData toTemplateData(TemplateDataDTO templateDataDTO);

    TemplateDataDTO toTemplateDataDTO(TemplateData templateData);

    List<TemplateDataDTO> toListTemplateDataDTO(List<TemplateData> templateDataList);
}
