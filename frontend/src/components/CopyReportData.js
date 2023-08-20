import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';

export default function CopyReportData({reportToCopy,closeModal,fetchReportData}) {
  const [reports, setReports] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 3; // Number of items per page

  useEffect(() => {
    fetchUserReports();
  }, []);

  const fetchUserReports = async () => {
    try {
      const response = await request('get', '/reports/inactive/user');
      setReports(response.data);
    } catch (error) {
      toast.error('Не удалось получить неактивные отчеты пользователя.');
    }
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString();
  };

  const copyReportData = async (selectedReport) => {
    try {
      await request('put', `/report/data/copy/${reportToCopy.id}`, selectedReport.id);
      toast.success("Данные успешно скопированы");
      fetchReportData();
      closeModal();
    } catch (error) {
      toast.error('Не удалось завершить отчет.');
    }
  };

  const getTotalPages = () => Math.max(Math.ceil(reports.length / pageSize), 1);

  useEffect(() => {
    // Ensure current page does not exceed total pages when data changes
    if (currentPage > getTotalPages()) {
      setCurrentPage(getTotalPages());
    }
  }, [reports, currentPage]);

  const startIndex = (currentPage - 1) * pageSize;
  const endIndex = startIndex + pageSize;
  const currentReports = reports.slice(startIndex, endIndex);

  return (
    <div className="bg-white w-1/2 mx-auto mt-64 p-2">
         <div className="flex justify-end mb-2">
          <button onClick={() => closeModal()}>
            <img
              className="h-6 md:border-0 hover:brightness-90"
              src={require("../pictures/close.png")}
              alt="Войти"
            />
          </button>
        </div>
      {reports.length === 0 ? (
        <p>Список отчетов пуст.</p>
      ) : (
        <>
          {currentReports.map((report) => (
            <div key={report.id} className="border border-gray-300 rounded p-4 mb-4">
              <div>
                <div className="font-bold text-lg border-b-2">{report.template.name}</div>
              </div>
              <div>
                <span className="font-semibold">Дата начала:</span> {formatDate(report.startDate)}
              </div>
              <div>
                <span className="font-semibold">Дата завершения:</span> {formatDate(report.endDate)}
              </div>
              {report.comment !== "" && (
                <div>
                  <strong>Комментарий:</strong> {report.comment}
                </div>
              )}
              <button
                onClick={() => copyReportData(report)}
                className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none mt-4"
              >
                Выбрать
              </button>
            </div>
          ))}
          <div className="flex items-center mt-4 px-4 py-2">
            <button
              disabled={currentPage === 1}
              onClick={() => setCurrentPage(currentPage - 1)}
              className={`px-4 py-2 rounded-lg border ${currentPage === 1 ? "bg-gray-200" : "bg-sky-200 text-blue-600"
                } mr-2`}
            >
              ❮
            </button>
            <span className="font-semibold text-blue-600">
              {currentPage}/{getTotalPages()}
            </span>
            <button
              disabled={currentPage >= getTotalPages()}
              onClick={() => setCurrentPage(currentPage + 1)}
              className={`px-4 py-2 rounded-lg border ${currentPage >= getTotalPages() ? "bg-gray-200" : "bg-sky-200 text-blue-600"
                } ml-2`}
            >
              ❯
            </button>
          </div>
        </>
      )}
    </div>
  );
}
