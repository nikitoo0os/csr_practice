import React, { useEffect, useState, useCallback } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';
import { useLocation } from 'react-router-dom';

export default function FillReport() {
  const [services, setServices] = useState([]);
  const [formData, setFormData] = useState([]);
  const location = useLocation();
  const report = location.state && location.state.report;

  useEffect(() => {
    fetchReportServices();
  }, []);

  const fetchReportServices = useCallback(async () => {
    try {
      const response = await request('get', `/report/data/services/${report.id}`);
      setServices(response.data);
      initializeFormData(response.data);
    } catch (error) {
      toast.error('Не удалось получить услуги отчета.');
    }
  }, [report.id]);

  const initializeFormData = (services) => {
    const initialData = services.map((service) => ({
      serviceId: service.id,
      count1: '',
      count2: '',
      percent1: '',
      percent2: '',
      regularAct: '',
    }));
    setFormData(initialData);
  };

  const handleInputChange = (event, serviceId, fieldName) => {
    const updatedData = formData.map((item) => {
      if (item.serviceId === serviceId) {
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
      for (const item of formData) {
        const { serviceId, count1, count2, percent1, percent2, regularAct } = item;
        if (
          count1.trim() !== '' ||
          count2.trim() !== '' ||
          percent1.trim() !== '' ||
          percent2.trim() !== '' ||
          regularAct.trim() !== ''
        ) {
          const data = {
            id:serviceId,
            count1: count1.trim() !== '' ? parseInt(count1) : null,
            count2: count2.trim() !== '' ? parseInt(count2) : null,
            percent1: percent1.trim() !== '' ? parseFloat(percent1) : null,
            percent2: percent2.trim() !== '' ? parseFloat(percent2) : null,
            regularAct,
          };
          await request('put', '/report/data', data);
        }
      }
      toast.success('Данные успешно сохранены.');
    } catch (error) {
      toast.error('Не удалось сохранить данные.');
    }
  };

  return (
    <>
      <div className="bg-white p-4">
        <table className="table-auto w-full border shadow-lg">
          <thead>
            <tr className="bg-sky-600 text-white">
              <th className="px-4 py-2 border w-10">№</th>
              <th className="px-4 py-2 border w-96">Наименование услуги</th>
              <th className="px-4 py-2 border w-64">Кол-во1</th>
              <th className="px-4 py-2 border w-64">Кол-во2</th>
              <th className="px-4 py-2 border w-64">Процент1</th>
              <th className="px-4 py-2 border w-64">Процент2</th>
              <th className="px-4 py-2 border w-96">Нормативный акт</th>
            </tr>
          </thead>
          <tbody>
            {services.map((service, index) => (
              <tr key={service.id}>
                <td className="border px-4 py-2">{index + 1}</td>
                <td className="border px-4 py-2">{service.name}</td>
                <td className="border px-4 py-2">
                  <input
                    type="text"
                    value={formData[index].count1}
                    onChange={(e) => handleInputChange(e, service.id, 'count1')}
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                  />
                </td>
                <td className="border px-4 py-2">
                  <input
                    type="text"
                    value={formData[index].count2}
                    onChange={(e) => handleInputChange(e, service.id, 'count2')}
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                  />
                </td>
                <td className="border px-4 py-2">
                  <input
                    type="text"
                    value={formData[index].percent1}
                    onChange={(e) => handleInputChange(e, service.id, 'percent1')}
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                  />
                </td>
                <td className="border px-4 py-2">
                  <input
                    type="text"
                    value={formData[index].percent2}
                    onChange={(e) => handleInputChange(e, service.id, 'percent2')}
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                  />
                </td>
                <td className="border px-4 py-2">
                  <input
                    type="text"
                    value={formData[index].regularAct}
                    onChange={(e) => handleInputChange(e, service.id, 'regularAct')}
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <button
          type="button"
          onClick={saveData}
          className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none mt-4"
        >
          Сохранить
        </button>
      </div>
    </>
  );
  
}
