package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.TemplateDataDTO;
import com.vyatsu.practiceCSR.entity.api.TemplateData;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-13T13:23:31+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class TemplateDataMapperImpl implements TemplateDataMapper {

    @Override
    public TemplateData toTemplateData(TemplateDataDTO templateDataDTO) {
        if ( templateDataDTO == null ) {
            return null;
        }

        TemplateData templateData = new TemplateData();

        templateData.setId( templateDataDTO.getId() );
        templateData.setTemplate( templateDataDTO.getTemplate() );
        templateData.setService( templateDataDTO.getService() );

        return templateData;
    }
}
