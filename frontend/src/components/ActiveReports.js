import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

export default function ActiveReports() {
  const [reports, setReports] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 5; // Number of items per page
  const navigate = useNavigate();

  useEffect(() => {
    fetchUserReports();
  }, []);

  const fetchUserReports = async () => {
    try {
      const response = await request('get', '/reports/active/user');
      const sortedReports = response.data.sort((a, b) => {
        const dateA = new Date(a.endDate);
        const dateB = new Date(b.endDate);
        return dateB - dateA;
      });
      setReports(sortedReports);
    } catch (error) {
      toast.error('Не удалось получить отчеты пользователя.');
    }
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString();
  };

  const handleFillReport = (report) => {
    navigate(`/fill-report`, { state: { report } });
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
    <div>
      {currentReports.map((report) => (
        <div key={report.id} className="border border-gray-300 rounded p-4 mb-4">
          <div>
            <strong>Дата начала:</strong> {formatDate(report.startDate)}
          </div>
          <div>
            <strong>Дата завершения:</strong> {formatDate(report.endDate)}
          </div>
          {report.comment !== "" && (
            <div>
              <strong className="text-orange-500">Комментарий:</strong> {report.comment}
            </div>
          )}
          <button
            onClick={() => handleFillReport(report)}
            className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none mt-4"
          >
            Заполнить отчет
          </button>
        </div>
      ))}
       <div className="flex items-center mt-4 px-4 py-2">
        <button
          disabled={currentPage === 1}
          onClick={() => setCurrentPage(currentPage - 1)}
          className={`px-4 py-2 rounded-lg border ${
            currentPage === 1 ? "bg-gray-200" : "bg-sky-200 text-blue-600"
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
          className={`px-4 py-2 rounded-lg border ${
            currentPage >= getTotalPages() ? "bg-gray-200" : "bg-sky-200 text-blue-600"
          } ml-2`}
        >
          ❯
        </button>
      </div>
    </div>
  );
}