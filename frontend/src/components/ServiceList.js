import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';
import ReactModal from 'react-modal';

export default function ServiceList() {
  const [services, setServices] = useState([]);
  const [name, setServiceName] = useState('');
  const [serviceToDelete, setServiceToDelete] = useState(null);
  const [filterText, setFilterText] = useState('');

  const handleFilterChange = (event) => {
    setFilterText(event.target.value);
  };



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

  const addService = async (event) => {
    event.preventDefault();
    try {
      if (name.length > 0) {
        const serviceData = { name };
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

  const openDeleteConfirmationModal = (serviceId) => {
    setServiceToDelete(serviceId);
  };

  const closeDeleteConfirmationModal = () => {
    setServiceToDelete(null);
  };


  return (
    <>
      <div className="mx-auto bg-white p-4">
        <h2 className="text-xl font-bold text-gray-800 mb-4">Добавление услуги</h2>
        <form onSubmit={addService}>
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
              type="submit" // Изменили здесь
              className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
            >
              Добавить
            </button>
          </div>
        </form>
        <h2 className="text-xl font-bold text-gray-800 mb-4">Список услуг</h2>
        <div className="border-2 w-full p-2 mb-2 mt-4 shadow-lg rounded">
        <h2 className="font-semibold border-b-2">Фильтр</h2>
        <div className="flex flex-col mt-4">
        <label className="text-sm mb-1">Название услуги</label>
        <input
            type="text"
            value={filterText}
            onChange={handleFilterChange}
            placeholder="Введите название услуги"
            className="w-full border border-gray-300 focus:outline-none rounded-md px-4 py-2 mb-2 focus:border-sky-500"
          />
        </div>
        </div>
        <div className="max-w-7xl max-h-96 overflow-y-auto shadow-xl rounded border-2 p-2">
          <ul>
            {services
              .filter((service) =>
                service.name.toLowerCase().includes(filterText.toLowerCase())
              )
              .map((service, index) => (
                <li
                  key={service.id}
                  className={`mb-2 flex items-center ${index % 2 === 0 ? 'bg-gray-200' : 'bg-white'
                    }`}
                >
                  <span className="w-10/12 p-1">
                    {index + 1}. {service.name}
                  </span>
                  <button
                    onClick={() => openDeleteConfirmationModal(service.id)}
                    className="w-2/12 p-1 mr-2 text-red-500 hover:text-red-600 focus:outline-none text-right"
                  >
                    Удалить
                  </button>
                </li>
              ))}
          </ul>
        </div>
      </div>
      <ReactModal
        isOpen={serviceToDelete !== null}
        onRequestClose={closeDeleteConfirmationModal}
        contentLabel="Подтвердите удаление"
        className="Modal"
        overlayClassName="Overlay"
      >
        <div className="text-center bg-white w-1/3 mx-auto p-4">
          <p>Вы действительно хотите удалить услугу?</p>
          <div className="mt-4">
            <button
              onClick={() => {
                deleteService(serviceToDelete);
                closeDeleteConfirmationModal();
              }}
              className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded mr-2 shadow-md"
            >
              Подтвердить
            </button>
            <button
              onClick={closeDeleteConfirmationModal}
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
