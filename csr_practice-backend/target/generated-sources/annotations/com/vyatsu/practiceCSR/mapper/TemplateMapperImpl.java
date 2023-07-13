package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.TemplateDTO;
import com.vyatsu.practiceCSR.entity.api.Template;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-13T13:23:31+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class TemplateMapperImpl implements TemplateMapper {

    @Override
    public TemplateDTO toTemplateDTO(Template template) {
        if ( template == null ) {
            return null;
        }

        TemplateDTO.TemplateDTOBuilder templateDTO = TemplateDTO.builder();

        if ( template.getId() != null ) {
            templateDTO.id( template.getId() );
        }
        templateDTO.date( template.getDate() );
        templateDTO.name( template.getName() );

        return templateDTO.build();
    }

    @Override
    public List<TemplateDTO> toTemplateDTOList(List<Template> templates) {
        if ( templates == null ) {
            return null;
        }

        List<TemplateDTO> list = new ArrayList<TemplateDTO>( templates.size() );
        for ( Template template : templates ) {
            list.add( toTemplateDTO( template ) );
        }

        return list;
    }

    @Override
    public Template toTemplate(TemplateDTO templateDTO) {
        if ( templateDTO == null ) {
            return null;
        }

        Template template = new Template();

        template.setId( templateDTO.getId() );
        template.setDate( templateDTO.getDate() );
        template.setName( templateDTO.getName() );

        return template;
    }
}
