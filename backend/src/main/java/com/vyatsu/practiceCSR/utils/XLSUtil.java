package com.vyatsu.practiceCSR.utils;

import com.vyatsu.practiceCSR.entity.api.ReportData;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class XLSUtil {

    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    // private static InputStream getTemplateInputStream() throws IOException {
    // File file = ResourceUtils.getFile("classpath:template.xlsx");;
    // return new FileInputStream(file);
    // }
    public ByteArrayInputStream createXLSX(List<ReportData> dataList, String[] headers, HttpServletResponse response) throws IOException {
        out = new ByteArrayOutputStream();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            int rowNum = 0;
            Row headerRow = sheet.createRow(rowNum++);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int summaryCount1 = 0, summaryCount2 = 0;
            double summaryPercent = 0.0;

            for (ReportData data : dataList) {
                Row row = sheet.createRow(rowNum);

                row.createCell(0).setCellValue(rowNum++);
                row.createCell(1).setCellValue(data.getService().getName());
                row.createCell(2).setCellValue(data.getCount1());
                summaryCount1 += data.getCount1();
                row.createCell(3).setCellValue(data.getCount2());
                summaryCount2 += data.getCount2();
                row.createCell(4).setCellValue(String.valueOf(data.getPercent1()));
                row.createCell(5).setCellValue(String.valueOf(data.getPercent2()));
                row.createCell(6).setCellValue(data.getRegularAct());
            }
            summaryPercent = (double) (summaryCount2 * 100) / summaryCount1;

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue("ИТОГО по всем ОМСУ:");
            row.createCell(1).setCellValue(summaryCount1);
            row.createCell(2).setCellValue(summaryCount2);
            row.createCell(3).setCellValue(summaryPercent);

            // String outputPath = "C:/Windows/Temp/Csr/output.xlsx"; // Change this to your
            // desired output path
            // File outputFile = new File(outputPath);
            try {
                workbook.write(response.getOutputStream());
                workbook.write(out);
                out.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
