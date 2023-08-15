import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

export default function InactiveReports() {
  const [reports, setReports] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 5; // Number of items per page
  const navigate = useNavigate();

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

  const handleViewReport = (report) => {
    navigate(`/read-report`, { state: { report } });
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
            <div className="font-bold text-lg border-b-2">{report.template.name}</div>
          </div>
          <div>
            <span className="font-semibold">Дата начала:</span> {formatDate(report.startDate)}
          </div>
          <div>
            <span className="font-semibold">Дата завершения:</span> {formatDate(report.endDate)}
          </div>
          {report.comment !== null && (
            <div>
              <strong>Комментарий:</strong> {report.comment}
            </div>
          )}
          <button
            onClick={() => handleViewReport(report)}
            className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none mt-4"
          >
            Посмотреть
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