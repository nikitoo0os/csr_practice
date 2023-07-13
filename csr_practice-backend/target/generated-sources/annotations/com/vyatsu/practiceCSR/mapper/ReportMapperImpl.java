package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.dto.api.TemplateDTO;
import com.vyatsu.practiceCSR.entity.api.Region;
import com.vyatsu.practiceCSR.entity.api.Report;
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
public class ReportMapperImpl implements ReportMapper {

    @Override
    public ReportDTO toReportDTO(Report report) {
        if ( report == null ) {
            return null;
        }

        ReportDTO.ReportDTOBuilder reportDTO = ReportDTO.builder();

        reportDTO.id( report.getId() );
        reportDTO.template( templateToTemplateDTO( report.getTemplate() ) );
        reportDTO.region( regionToRegionDTO( report.getRegion() ) );
        reportDTO.frequency( report.getFrequency() );
        reportDTO.startDate( report.getStartDate() );
        reportDTO.endDate( report.getEndDate() );
        reportDTO.activeDays( report.getActiveDays() );
        reportDTO.comment( report.getComment() );
        reportDTO.isActive( report.getIsActive() );
        reportDTO.isCompleted( report.getIsCompleted() );

        return reportDTO.build();
    }

    @Override
    public Report toReport(ReportDTO reportDTO) {
        if ( reportDTO == null ) {
            return null;
        }

        Report report = new Report();

        report.setId( reportDTO.getId() );
        report.setTemplate( templateDTOToTemplate( reportDTO.getTemplate() ) );
        report.setRegion( regionDTOToRegion( reportDTO.getRegion() ) );
        report.setFrequency( reportDTO.getFrequency() );
        report.setStartDate( reportDTO.getStartDate() );
        report.setEndDate( reportDTO.getEndDate() );
        report.setActiveDays( reportDTO.getActiveDays() );
        report.setComment( reportDTO.getComment() );
        report.setIsActive( reportDTO.getIsActive() );
        report.setIsCompleted( reportDTO.getIsCompleted() );

        return report;
    }

    @Override
    public List<ReportDTO> toListReportDTO(List<Report> reports) {
        if ( reports == null ) {
            return null;
        }

        List<ReportDTO> list = new ArrayList<ReportDTO>( reports.size() );
        for ( Report report : reports ) {
            list.add( toReportDTO( report ) );
        }

        return list;
    }

    protected TemplateDTO templateToTemplateDTO(Template template) {
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

    protected RegionDTO regionToRegionDTO(Region region) {
        if ( region == null ) {
            return null;
        }

        RegionDTO.RegionDTOBuilder regionDTO = RegionDTO.builder();

        if ( region.getId() != null ) {
            regionDTO.id( region.getId() );
        }
        regionDTO.name( region.getName() );

        return regionDTO.build();
    }

    protected Template templateDTOToTemplate(TemplateDTO templateDTO) {
        if ( templateDTO == null ) {
            return null;
        }

        Template template = new Template();

        template.setId( templateDTO.getId() );
        template.setDate( templateDTO.getDate() );
        template.setName( templateDTO.getName() );

        return template;
    }

    protected Region regionDTOToRegion(RegionDTO regionDTO) {
        if ( regionDTO == null ) {
            return null;
        }

        Region region = new Region();

        region.setId( regionDTO.getId() );
        region.setName( regionDTO.getName() );

        return region;
    }
}
