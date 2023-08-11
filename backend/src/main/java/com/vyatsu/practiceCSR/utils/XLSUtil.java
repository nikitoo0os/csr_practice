package com.vyatsu.practiceCSR.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class XLSUtil {
    public static <T> byte[] writeDataToByteArray(List<T> dataList, String[] headers) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            int rowNum = 0;
            Row headerRow = sheet.createRow(rowNum++);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            for (T data : dataList) {
                Row row = sheet.createRow(rowNum++);
                // Customize this part based on the type of data you have
                // For example, if your data is a Map or a POJO, you can extract values accordingly
                // row.createCell(0).setCellValue(data.getId());
                // row.createCell(1).setCellValue(data.getName());
                // ...
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
                return outputStream.toByteArray();
        }
    }
}
