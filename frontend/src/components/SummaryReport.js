import React, { useState } from "react";
import { request } from "../helpers/axios_helper";
import ExcelJS from "exceljs";
import { useNavigate } from 'react-router-dom';

export default function SummaryReport({ template, closeModal }) {
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [isDownloading, setIsDownloading] = useState(false);
  const navigate = useNavigate();

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
      templateId: template.id,
    };
    const response = await request("post", "/reports/summary", options);

    const responseData = response.data;
    const modifiedData = responseData.map((item) => {
      const { report, ...newItem } = item;
      return newItem;
    });

    const updatedData = modifiedData.map((item) => {
      const service = item.service.name;
      const { service: _, ...rest } = item;
      return {
        service,
        ...rest,
      };
    });

    const xlsxData = updatedData.map((item) => {
      const { ...rest } = item;
      return {
        number: "1",
        ...rest,
      };
    });

    xlsxData.forEach((item, index) => {
      item.number = index + 1;
    });
    const tableData = xlsxData;
    const table_head = {
      number: "№",
      service: "Наименование услуги в Кировской области",
      count1: template.countAllRequests,
      count2: template.countEPGURequests,
      percent1: template.percentEPGURequests,
      percent2: template.percentNotViolationEPGURequests,
    };
    xlsxData.unshift(table_head);

    const totalRow = {
      number: null,
      service: "ИТОГО по всем ОМСУ:",
      count1: updatedData.reduce((total, item) => total + item.count1, 0),
      count2: updatedData.reduce((total, item) => total + item.count2, 0),
      percent1:
      (
        (updatedData.reduce((total, item) => total + item.count2, 0) /
          updatedData.reduce((total, item) => total + item.count1, 0)) *
        100
      ).toFixed(2),
      percent2: null,
    };

    xlsxData.push(totalRow);

    
    navigate(`/summary-report`, { state: { tableData } });
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
