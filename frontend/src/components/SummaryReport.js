import React, { useState } from "react";
import { request } from "../helpers/axios_helper";
import ExcelJS from 'exceljs';



export default function SummaryReport({ template, closeModal }) {
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [isDownloading, setIsDownloading] = useState(false);

  const handleStartDateChange = (event) => {
    setStartDate(event.target.value);
  };

  const handleEndDateChange = (event) => {
    setEndDate(event.target.value);
  };

  const handleGenerateReport = async () => {
    const options = {
      startDate,
      endDate,
      templateId: template.id
    };
    const response = await request('post', '/reports/summary', options);
  
    const responseData = response.data;
    const modifiedData = responseData.map(item => {
      const { report, ...newItem } = item;
      return newItem;
    });
  
    const updatedData = modifiedData.map(item => {
      const service = item.service.name;
      const { service: _, ...rest } = item;
      return {
        service,
        ...rest,
      };
    });
    const table_head = {
      service: "Наименование услуги в Кировской области",
      count1: template.countAllRequests,
      count2: template.countEPGURequests,
      percent1: template.percentEPGURequests,
      percent2: template.percentNotViolationEPGURequests
    };
    updatedData.unshift(table_head);

    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet("MySheet1");

    const columnWidths = [50, 25, 25, 25, 25];
    worksheet.columns = columnWidths.map(width => ({ width }));  

    updatedData.forEach(item => {
      worksheet.addRow(Object.values(item));
    });

    worksheet.eachRow((row, rowNumber) => {
      row.height = 110;
      row.alignment = {
        vertical: 'middle',
        wrapText: true,
      };
    });

    worksheet.eachRow((row, rowNumber) => {
      row.eachCell((cell, colNumber) => {
        cell.border = {
          top: { style: 'thin' },
          left: { style: 'thin' },
          bottom: { style: 'thin' },
          right: { style: 'thin' },
        };
      });
    });

    const firstRow = worksheet.getRow(1);
  firstRow.eachCell((cell, colNumber) => {
    if (colNumber <= 5) {
      cell.font = { bold: true };
      cell.fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: 'fff2cc' },
      };
      cell.alignment = { horizontal: 'center', vertical: 'middle' };
    }
  });

  worksheet.eachRow((row, rowIndex) => {
    if (rowIndex >= 2) {
      row.eachCell((cell, colNumber) => {
        if (colNumber >= 2) {
          cell.alignment = { horizontal: 'center', vertical: 'middle' };
        }
      });
    }
  });

    const buffer = await workbook.xlsx.writeBuffer();
    const blob = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'MyExcel.xlsx';
    a.click();
    window.URL.revokeObjectURL(url);
  };
  

  return (
    <div className="p-4 bg-white rounded shadow-md w-48 mx-auto mt-64">
      <div className="flex justify-end">
        <button onClick={() => closeModal()}>
          <img
            className="h-6 md:border-0 hover:brightness-90"
            src={require("../pictures/close.png")}
            alt="Войти"
          />
        </button>
      </div>
      <div className="mb-4">
        <label htmlFor="startDate" className="mr-2">
          Дата начала:
        </label>
        <input
          type="date"
          id="startDate"
          value={startDate}
          onChange={handleStartDateChange}
          min="2022-01-01"
          max={new Date().toISOString().split("T")[0]}
          className="border border-gray-300 rounded px-3 py-1"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="endDate" className="mr-2">
          Дата окончания:
        </label>
        <input
          type="date"
          id="endDate"
          value={endDate}
          onChange={handleEndDateChange}
          min="2022-01-01"
          max={new Date().toISOString().split("T")[0]}
          className="border border-gray-300 rounded px-3 py-1"
        />
      </div>
      <button
        onClick={handleGenerateReport}
        disabled={isDownloading}
        className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
      >
        {isDownloading ? "Формирование.." : "Сформировать"}
      </button>
    </div>
  );
}
