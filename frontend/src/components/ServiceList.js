import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';

export default function ServiceList() {
  const [services, setServices] = useState([]);
  const [serviceName, setServiceName] = useState('');

  useEffect(() => {
    fetchServices();
  }, []);

  const fetchServices = async () => {
    try {
      const response = await request('get', '/services');
      if (response.status === 200) {
        setServices(response.data);
      } else {
        // Обработка ошибки, если требуется
      }
    } catch (error) {
      // Обработка ошибки, если требуется
      toast.error('Ошибка при получении данных услуг');
    }
  };

  const addService = async () => {
    try {
      const newService = {
        name: serviceName
      };

      const response = await request('post', '/services', newService);

      if (response.status === 201) {
        toast.success('Услуга успешно добавлена');
        setServiceName('');
        fetchServices(); // Обновляем список услуг после добавления
      } else {
        // Обработка ошибки, если требуется
      }
    } catch (error) {
      // Обработка ошибки, если требуется
    }
  };

  return (
    <>
      <div className="max-w-screen-xl mx-auto bg-white shadow-md p-4">
        <h2 className="text-2xl font-bold text-gray-800 mb-4">Список услуг</h2>
        <ul className="list-disc pl-6">
          {services.map((service, index) => (
            <li key={service.id} className="mb-2">
              {index + 1}. {service.name}
            </li>
          ))}
        </ul>
        <div className="mt-4">
          <input
            type="text"
            value={serviceName}
            onChange={(e) => setServiceName(e.target.value)}
            placeholder="Введите название услуги"
            className="border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2 mr-2"
          />
          <button
            onClick={addService}
            className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
          >
            Добавить
          </button>
        </div>
      </div>
    </>
  );
}
