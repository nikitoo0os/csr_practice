import React, { useEffect, useState, useCallback } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';
import { useLocation } from 'react-router-dom';

export default function ReadReport() {
  const [reportsData, setReportsData] = useState([]);
  const location = useLocation();
  const report = location.state && location.state.report;

  useEffect(() => {
    fetchReportData();
  }, []);

  const fetchReportData = useCallback(async () => {
    try {
      const response = await request('get', `/report/data/services/${report.id}`);
      setReportsData(response.data);
    } catch (error) {
      toast.error('Не удалось получить услуги отчета.');
    }
  }, [report.id]);

  // Calculate the total count1 and count2
  const totalCount1 = reportsData.reduce((acc, item) => acc + (parseFloat(item.count1) || 0), 0);
  const totalCount2 = reportsData.reduce((acc, item) => acc + (parseFloat(item.count2) || 0), 0);

  // Calculate the percent1 for the total row
  const totalPercent1 = totalCount1 !== 0 ? ((totalCount2 * 100) / totalCount1).toFixed(1) : '';

  return (
    <>
      <div className="bg-white p-4">
        <table className="table-auto w-full border shadow-lg">
          <thead>
            <tr className="bg-sky-600 text-white">
              <th className="px-4 py-2 border w-10">№</th>
              <th className="px-4 py-2 border w-96">service name</th>
              <th className="px-4 py-2 border w-64">count1</th>
              <th className="px-4 py-2 border w-64">count2</th>
              <th className="px-4 py-2 border w-64">percent1</th>
              <th className="px-4 py-2 border w-64">percent2</th>
              <th className="px-4 py-2 border w-96">regular act</th>
            </tr>
          </thead>
          <tbody>
            {reportsData.map((reportData, index) => (
              <tr key={reportData.service.id}>
                <td className="border px-4 py-2">{index + 1}</td>
                <td className="border px-4 py-2">{reportData.service.name}</td>
                <td className="border px-4 py-2">{reportData.count1}</td>
                <td className="border px-4 py-2">{reportData.count2}</td>
                <td className="border px-4 py-2">{reportData.percent1}</td>
                <td className="border px-4 py-2">{reportData.percent2}</td>
                <td className="border px-4 py-2">{reportData.regularAct}</td>
              </tr>
            ))}
            <tr className="bg-gray-200">
              <td className="border border-gray-300 px-4 py-2 text-right font-semibold" colSpan="2">
                ИТОГО:
              </td>
              <td className="border border-gray-300 px-4 py-2 font-semibold">{totalCount1}</td>
              <td className="border border-gray-300 px-4 py-2 font-semibold">{totalCount2}</td>
              <td className="border border-gray-300 px-4 py-2 font-semibold">{totalPercent1}</td>
              <td className="border border-gray-300 px-4 py-2"></td>
              <td className="border border-gray-300 px-4 py-2"></td>
            </tr>
          </tbody>
        </table>
      </div>
    </>
  );
}
