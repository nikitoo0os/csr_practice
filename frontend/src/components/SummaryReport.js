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
    // Validation and report generation code here...

    try {
      setIsDownloading(true);

      const response = await request("post", "/reports/summary", {
        startDate: startDate,
        endDate: endDate,
        templateId: template.id,
      });

      if (response.data && response.data.fileUrl) {
        const downloadLink = document.createElement("a");
        downloadLink.href = response.data.fileUrl;
        downloadLink.target = "_blank";
        downloadLink.download = "summary-report.xlsx";
        document.body.appendChild(downloadLink);
        downloadLink.click();
        document.body.removeChild(downloadLink);
      }

      setIsDownloading(false);
    } catch (error) {
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
        id="downloadButton"
        onClick={handleGenerateReport}
        disabled={isDownloading}
        className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
      >
        {isDownloading ? "Формирование..." : "Сформировать"}
      </button>
      <script>
        {`
        document.getElementById('downloadButton').addEventListener('click', function() {
          fetch('/downloadExcel') // Update the URL to your actual download route
            .then(response => response.blob())
            .then(blob => {
              const url = window.URL.createObjectURL(blob);
              const a = document.createElement('a');
              a.style.display = 'none';
              a.href = url;
              a.download = 'summary-report.xlsx';
              document.body.appendChild(a);
              a.click();
              window.URL.revokeObjectURL(url);
            });
        });
        `}
      </script>
    </div>
  );
}
