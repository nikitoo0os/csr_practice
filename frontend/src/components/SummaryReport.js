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
    // Проверка корректности дат (проверка наличия обеих дат и их корректности)
    if (!startDate || !endDate) {
      toast.error("Пожалуйста, введите обе даты: начальную и конечную.");
      return;
    }

    const startDateObject = new Date(startDate);
    const endDateObject = new Date(endDate);

    if (isNaN(startDateObject) || isNaN(endDateObject)) {
      toast.error(
        "Некорректный формат даты. Пожалуйста, введите даты в формате гггг-мм-дд."
      );
      return;
    }

    if (startDateObject > endDateObject) {
      toast.error("Дата начала не может быть позже даты окончания.");
      return;
    }

    // Если проверка дат прошла успешно, продолжим формирование отчета
    try {
      setIsDownloading(true);

      // Отправляем HTTP-запрос на формирование отчета
      const response = await request("post", "/reports/summary", {
        startDate: startDate,
        endDate: endDate,
        templateId: template.id,
      });

      // Предполагается, что сервер возвращает URL скачиваемого файла, который можно использовать для скачивания файла
      if (response.data && response.data.fileUrl) {
        const link = document.createElement("a");
        link.href = response.data.fileUrl;
        link.target = "_blank";
        link.download = "summary-report.xlsx";
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
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
        <button onClick={() => closeModal() }>
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
    min="2022-01-01" // Минимальная дата начала
    max={new Date().toISOString().split('T')[0]} // Максимальная дата начала (текущая дата)
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
    min="2022-01-01" // Минимальная дата окончания
    max={new Date().toISOString().split('T')[0]} // Максимальная дата окончания (текущая дата)
    className="border border-gray-300 rounded px-3 py-1"
  />
</div>
      <button
        onClick={handleGenerateReport}
        disabled={isDownloading}
        className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
      >
        {isDownloading ? "Формирование..." : "Сформировать"}
      </button>
    </div>
  );
}
