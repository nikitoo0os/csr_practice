import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';

export default function ServiceList() {
  const [services, setServices] = useState([]);
  const [name, setServiceName] = useState('');

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
      if(name.length > 0 )
      {
      const serviceData = {name};
      const response = await request('post', '/services', serviceData);

      if (response.status === 200) {
        toast.success('Услуга успешно добавлена');
        setServiceName('');
        fetchServices(); // Обновляем список услуг после добавления
      } else {
        // Обработка ошибки, если требуется
      }
    }
    } catch (error) {
      // Обработка ошибки, если требуется
    }
  };

  const deleteService = async (serviceId) => {
    try {
      const response = await request('delete', `/services/${serviceId}`);

      if (response.status === 200) {
        toast.success('Услуга успешно удалена');
        fetchServices(); // Обновляем список услуг после удаления
      } else {
        // Обработка ошибки, если требуется
      }
    } catch (error) {
      // Обработка ошибки, если требуется
    }
  };

  return (
    <>
      <div className="mx-auto bg-white p-4">
        <h2 className="text-2xl font-bold text-gray-800 mb-4">Добавление услуги</h2>
        <form>
        <div className="mb-4">
          <textarea
            value={name}
            onChange={(e) => setServiceName(e.target.value)}
            placeholder="Введите название услуги"
            className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2 max-h-64"
            required
          />
        </div>
        <div className="mb-4">
          <button
            onClick={addService}
            className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
          >
            Добавить
          </button>
        </div>
        </form>
        <h2 className="text-2xl font-bold text-gray-800 mb-4">Список услуг</h2>
        <div className="max-w-7xl max-h-96 overflow-y-auto shadow-xl rounded border-2 p-2">
          <ul>
            {services.map((service, index) => (
              <li
                key={service.id}
                className={`mb-2 flex items-center ${
                  index % 2 === 0 ? 'bg-gray-200' : 'bg-white'
                }`}
              >
                <span className="w-10/12 p-1">
                  {index + 1}. {service.name}
                </span>
                <button
                  onClick={() => deleteService(service.id)}
                  className="w-2/12 p-1 mr-2 text-red-500 hover:text-red-600 focus:outline-none text-right"
                >
                  Удалить
                </button>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </>
  );
}
