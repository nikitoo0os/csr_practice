import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

export default function ActiveReports() {
  const [reports, setReports] = useState([]);
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

  return (
    <div>
      {reports.map((report) => (
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
    </div>
  );
}
