import React, { useEffect, useState, useCallback } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';
import { useLocation } from 'react-router-dom';
import ReactModal from 'react-modal';
import { useHistory } from 'react-router-dom';


export default function FillReport() {
  const [reportsData, setReportsData] = useState([]);
  const [formData, setFormData] = useState([]);
  const location = useLocation();
  const report = location.state && location.state.report;
  // Calculate the total count1 and count2
  const totalCount1 = formData.reduce((acc, item) => acc + (parseFloat(item.count1) || 0), 0);
  const totalCount2 = formData.reduce((acc, item) => acc + (parseFloat(item.count2) || 0), 0);

  // Calculate the percent1 for the total row
  const totalPercent1 = totalCount1 !== 0 ? ((totalCount2 * 100) / totalCount1).toFixed(1) : '';
  const [isConfirmationModalOpen, setIsConfirmationModalOpen] = useState(false);
  <button
    type="button"
    onClick={() => setIsConfirmationModalOpen(true)}
    className="bg-green-500 hover:bg-green-600 text-white font-bold ml-2 py-2 px-4 rounded focus:outline-none mt-4"
  >
    Завершить
  </button>



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
        const updatedItem = {
          ...item,
          [fieldName]: event.target.value,
        };
  
        if (fieldName === 'count1' || fieldName === 'count2') {
          const count1 = parseFloat(updatedItem.count1);
          const count2 = parseFloat(updatedItem.count2);
  
          if (!isNaN(count1) && !isNaN(count2) && count1 !== 0) {
            const newPercent1 = (count2 * 100) / count1;
            if (newPercent1 <= 100) {
              updatedItem.percent1 = newPercent1.toFixed(1);
            } else {
              // Adjust count2 to ensure percent1 is not greater than 100
              updatedItem.count2 = ((count1 * 100) / 100).toFixed(1);
              updatedItem.percent1 = 100;
            }
          } else {
            updatedItem.percent1 = '';
          }
        }
  
        if (fieldName === 'percent2') {
          const newPercent2 = parseFloat(updatedItem.percent2);
          if (!isNaN(newPercent2)) {
            updatedItem.percent2 = Math.min(newPercent2, 100).toFixed(1);
          }
        }
  
        return updatedItem;
      }
      return item;
    });
    setFormData(updatedData);
  };
  
  

  const saveData = async () => {
    try {
      const requestData = formData.map((item) => {
        const { id, service, count1, count2, percent1, percent2, regularAct } = item;
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

  const handleFinishReport = async () => {
    setIsConfirmationModalOpen(false);
    try {
      saveData();
      await request('put', `/reports/end/${report.id}`);
      toast.success('Отчет успешно завершен.');
      window.location.href = '/';
    } catch (error) {
      toast.error('Не удалось завершить отчет.');
    }
  };
  const handleCloseConfirmationModal = () => {
    setIsConfirmationModalOpen(false);
  };


  const copyReportData = async () => {
    try {
      await request('put', `/report/data/copy/${report.id}`);
      saveData();
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
                    readOnly={true}
                    onChange={(e) => handleInputChange(e, reportData.service, 'percent1')}
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                  />
                </td>
                <td className="border px-4 py-2">
                  <input
                    type="number"
                    min="0.0"
                    max="100.0"
                    step="0.5"
                    value={formData[index].percent2}
                    onChange={(e) => handleInputChange(e, reportData.service, 'percent2')}
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                  />
                </td>
                <td className="border px-4 py-2">
                  <textarea
                    value={formData[index].regularAct}
                    onChange={(e) => handleInputChange(e, reportData.service, 'regularAct')}
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1 max-h-96"
                  />
                </td>
              </tr>
            ))}
            <tr className="bg-gray-200">
              <td className="border border-gray-300 px-4 py-2 text-right font-semibold" colSpan="2">ИТОГО:</td>
              <td className="border border-gray-300 px-4 py-2 font-semibold">{totalCount1}</td>
              <td className="border border-gray-300 px-4 py-2 font-semibold">{totalCount2}</td>
              <td className="border border-gray-300 px-4 py-2 font-semibold">{totalPercent1}</td>
              <td className="border border-gray-300 px-4 py-2"></td>
              <td className="border border-gray-300 px-4 py-2"></td>
            </tr>
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
            onClick={() => setIsConfirmationModalOpen(true)}
            className="bg-green-500 hover:bg-green-600 text-white font-bold ml-2 py-2 px-4 rounded focus:outline-none mt-4"
          >
            Завершить
          </button>
          <button
            type="button"
            onClick={copyReportData}
            className="bg-lime-500 hover:bg-lime-600 text-white font-bold ml-2 py-2 px-4 rounded focus:outline-none mt-4"
          >
            Скопировать данные из предыдущего отчета
          </button>
        </div>
      </div>
      <ReactModal
        isOpen={isConfirmationModalOpen}
        onRequestClose={handleCloseConfirmationModal}
        contentLabel="Confirmation Modal"
        className="Modal"
        overlayClassName="Overlay"
      >
        <div className="bg-white w-96 mx-auto p-4 rounded">
          <p className="text-center">Вы уверены, что хотите завершить отчет?</p>
          <div className="flex justify-center mt-4">
            <button
              onClick={handleFinishReport}
              className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded mr-2 shadow-md"
            >
              Да
            </button>
            <button
              onClick={handleCloseConfirmationModal}
              className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded shadow-md"
            >
              Отмена
            </button>
          </div>
        </div>
      </ReactModal>
    </>
  );
}