import React, { useEffect, useState, useCallback } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';
import { useLocation } from 'react-router-dom';

export default function FillReport() {
  const [reportsData, setReportsData] = useState([]);
  const [formData, setFormData] = useState([]);
  const location = useLocation();
  const report = location.state && location.state.report;

  useEffect(() => {
    fetchReportData();
  }, []);

  const fetchReportData = useCallback(async () => {
    try {
      const response = await request('get', `/report/data/services/${report.id}`);
      setReportsData(response.data);
      initializeFormData(response.data);
    } catch (error) {
      toast.error('Не удалось получить услуги отчета.');
    }
  }, [report.id]);

  const initializeFormData = (data) => {
    const initialData = data.map((item) => ({
      id: item.id,
      service: item.service,
      count1: item.count1,
      count2: item.count2,
      percent1: item.percent1,
      percent2: item.percent2,
      regularAct: item.regularAct,
    }));
    setFormData(initialData);
  };

  const handleInputChange = (event, service, fieldName) => {
    const updatedData = formData.map((item) => {
      if (item.service.id === service.id) {
        return {
          ...item,
          [fieldName]: event.target.value,
        };
      }
      return item;
    });
    setFormData(updatedData);
  };

  const saveData = async () => {
    try {
      const requestData = formData.map((item) => {
        const { id,service, count1, count2, percent1, percent2, regularAct } = item;
        return {
          id: id,
          service: service,
          report: report,
          count1: parseInt(count1),
          count2: parseInt(count2),
          percent1: parseFloat(percent1),
          percent2: parseFloat(percent2),
          regularAct,
        };
      });
  
      await request('put', '/report/data', requestData);
      toast.success('Данные успешно сохранены.');
    } catch (error) {
      toast.error('Не удалось сохранить данные.');
    }
  };
  

  const finishReport = async () => {
    try {
      saveData();
      await request('put', `/reports/end/${report.id}`);
      toast.success('Отчет успешно завершен.');
    } catch (error) {
      toast.error('Не удалось завершить отчет.');
    }
  };

  return (
    <>
      <div className="bg-white p-4">
        <table className="table-auto w-full border shadow-lg">
          <thead>
            <tr className="bg-sky-600 text-white">
              <th className="px-4 py-2 border w-10">№</th>
              <th className="px-4 py-2 border w-96">Наименование услуги в Кировской области</th>
              <th className="px-4 py-2 border w-64">Количество обращений за отчетный период с учетом всех способов подачи (нарастающим итогом с 01.01.2023 по 30.06.2023)</th>
              <th className="px-4 py-2 border w-64">Количество обращений, поступивших в эл виде через ЕПГУ (нарастающим итогом с 01.01.2023 по 30.06.2023)</th>
              <th className="px-4 py-2 border w-64">% обращений в эл виде через ЕПГУ (целевой показатель на 2023 год 40%) (нарастающим итогом с 01.01.2023 по 30.06.2023)</th>
              <th className="px-4 py-2 border w-64">Доля услуг, предоставленных без нарушения регламентного срока при оказании услуги через ЕПГУ (нарастающим итогом с 01.01.2023 по 30.06.2023) (%)</th>
              <th className="px-4 py-2 border w-96">Наименование муниципального нормативного акта (административного регламента), которые приведены в соответствие с описанием целевого состояния (ОЦС) или типовым федеральным регламентом (номер и дата документа)</th>
            </tr>
          </thead>
          <tbody>
            {reportsData.map((reportData, index) => (
              <tr key={reportData.service.id}>
                <td className="border px-4 py-2">{index + 1}</td>
                <td className="border px-4 py-2">{reportData.service.name}</td>
                <td className="border px-4 py-2">
                  <input
                    type="number"
                    min="0"
                    value={formData[index].count1}
                    onChange={(e) => handleInputChange(e, reportData.service, 'count1')}
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                  />
                </td>
                <td className="border px-4 py-2">
                  <input
                    type="number"
                    min="0"
                    value={formData[index].count2}
                    onChange={(e) => handleInputChange(e, reportData.service, 'count2')}
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                  />
                </td>
                <td className="border px-4 py-2">
                  <input
                    type="number"
                    min="0.0"
                    max="100.0"
                    step="0.1"
                    value={formData[index].percent1}
                    onChange={(e) => handleInputChange(e, reportData.service, 'percent1')}
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                  />
                </td>
                <td className="border px-4 py-2">
                  <input
                    type="number"
                    min="0.0"
                    max="100.0"
                    step="0.1"
                    value={formData[index].percent2}
                    onChange={(e) => handleInputChange(e, reportData.service, 'percent2')}
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                  />
                </td>
                <td className="border px-4 py-2">
                  <input
                    type="text"
                    value={formData[index].regularAct}
                    onChange={(e) => handleInputChange(e, reportData.service, 'regularAct')}
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div>
          <button
            type="button"
            onClick={saveData}
            className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none mt-4"
          >
            Сохранить
          </button>
          <button
            type="button"
            onClick={finishReport}
            className="bg-green-500 hover:bg-green-600 text-white font-bold ml-2 py-2 px-4 rounded focus:outline-none mt-4"
          >
            Завершить
          </button>
        </div>
      </div>
    </>
  );
}
