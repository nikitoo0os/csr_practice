import React, { useState } from "react";
import { toast } from "react-toastify";
import { request } from "../helpers/axios_helper";

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
    try {
      setIsDownloading(true);

      const options = {
        startDate,
        endDate,
        templateId:template.id,
      };

      const response = await request('post', '/reports/summary', options, {responseType: 'blob'});

      const blob = new Blob([response.data], { type: "xlsx" });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      document.body.appendChild(link);
      link.setAttribute('style','display: none');
      link.setAttribute('target','blank');
      link.href = url;
      link.download = "summary-report.xlsx"; // Use "download" attribute
      link.click();
      window.URL.revokeObjectURL(url);
      link.remove();
      setIsDownloading(false);
    } catch (error) {
      console.error("Error generating report:", error);
      toast.error("Ошибка при формировании отчета.");
      setIsDownloading(false);
    }
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
