package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.ReportDataDTO;
import com.vyatsu.practiceCSR.entity.api.ReportData;
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
public class ReportDataMapperImpl implements ReportDataMapper {

    @Override
    public List<ReportDataDTO> toReportDataDTO(List<ReportData> reportData) {
        if ( reportData == null ) {
            return null;
        }

        List<ReportDataDTO> list = new ArrayList<ReportDataDTO>( reportData.size() );
        for ( ReportData reportData1 : reportData ) {
            list.add( reportDataToReportDataDTO( reportData1 ) );
        }

        return list;
    }

    @Override
    public List<ReportData> toReportData(List<ReportDataDTO> reportDataDTOs) {
        if ( reportDataDTOs == null ) {
            return null;
        }

        List<ReportData> list = new ArrayList<ReportData>( reportDataDTOs.size() );
        for ( ReportDataDTO reportDataDTO : reportDataDTOs ) {
            list.add( reportDataDTOToReportData( reportDataDTO ) );
        }

        return list;
    }

    protected ReportDataDTO reportDataToReportDataDTO(ReportData reportData) {
        if ( reportData == null ) {
            return null;
        }

        ReportDataDTO.ReportDataDTOBuilder reportDataDTO = ReportDataDTO.builder();

        reportDataDTO.id( reportData.getId() );
        reportDataDTO.service( reportData.getService() );
        reportDataDTO.report( reportData.getReport() );
        reportDataDTO.count1( reportData.getCount1() );
        reportDataDTO.count2( reportData.getCount2() );
        reportDataDTO.percent1( reportData.getPercent1() );
        reportDataDTO.percent2( reportData.getPercent2() );
        reportDataDTO.regularAct( reportData.getRegularAct() );

        return reportDataDTO.build();
    }

    protected ReportData reportDataDTOToReportData(ReportDataDTO reportDataDTO) {
        if ( reportDataDTO == null ) {
            return null;
        }

        ReportData reportData = new ReportData();

        reportData.setId( reportDataDTO.getId() );
        reportData.setService( reportDataDTO.getService() );
        reportData.setReport( reportDataDTO.getReport() );
        reportData.setCount1( reportDataDTO.getCount1() );
        reportData.setCount2( reportDataDTO.getCount2() );
        reportData.setPercent1( reportDataDTO.getPercent1() );
        reportData.setPercent2( reportDataDTO.getPercent2() );
        reportData.setRegularAct( reportDataDTO.getRegularAct() );

        return reportData;
    }
}
