import React, { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import { request } from '../helpers/axios_helper';

export default function EditUser({ userId }) {
  const [surname, setSurname] = useState('');
  const [firstname, setFirstname] = useState('');
  const [patronymic, setPatronymic] = useState('');
  const [email, setEmail] = useState('');
  const [region_id, setRegion] = useState('');
  const [regionList, setRegionList] = useState([]);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await request('get', `/users/${userId}`);
        if (response.status === 200) {
          const user = response.data;
          setSurname(user.surname);
          setFirstname(user.firstname);
          setPatronymic(user.patronymic);
          setEmail(user.email);
          setRegion(user.region_id);
        } else {
          // Обработка ошибки, если требуется
        }
      } catch (error) {
        // Обработка ошибки, если требуется
      }
    };

    const fetchRegions = async () => {
      try {
        const response = await request('get', '/regions');
        if (response.status === 200) {
          setRegionList(response.data);
        } else {
          // Обработка ошибки, если требуется
        }
      } catch (error) {
        // Обработка ошибки, если требуется
      }
    };

    fetchUser();
    fetchRegions();
  }, [userId]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const userData = {
        surname,
        firstname,
        patronymic,
        email,
        region_id,
      };

      // Отправляем данные на сервер
      const response = await request('put', `/users/${userId}`, userData);

      // Проверяем успешный статус ответа
      if (response.status === 200) {
        toast.success('Данные пользователя успешно изменены');
      } else {
        // Обработка ошибки, если требуется
      }
    } catch (error) {
      // Обработка ошибки, если требуется
    }
  };

  return (
    <>
      <div className="w-1/4 bg-white rounded p-4 mx-auto mt-56">
        <h2 className="text-2xl font-bold text-gray-800 mb-4">Изменить данные пользователя</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-800" htmlFor="surname">
              Фамилия
            </label>
            <input
              type="text"
              id="surname"
              name="surname"
              value={surname}
              onChange={(e) => setSurname(e.target.value)}
              className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-800" htmlFor="firstname">
              Имя
            </label>
            <input
              type="text"
              id="firstname"
              name="firstname"
              value={firstname}
              onChange={(e) => setFirstname(e.target.value)}
              className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-800" htmlFor="patronymic">
              Отчество
            </label>
            <input
              type="text"
              id="patronymic"
              name="patronymic"
              value={patronymic}
              onChange={(e) => setPatronymic(e.target.value)}
              className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-800" htmlFor="email">
              Электронная почта
            </label>
            <input
              type="email"
              id="email"
              name="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-800" htmlFor="region">
              Район
            </label>
            <select
              id="region"
              name="region"
              value={region_id}
              onChange={(e) => setRegion(e.target.value)}
              className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
              required
            >
              <option value="">Выберите район</option>
              {regionList.map((region) => (
                <option key={region.id} value={region.id}>
                  {region.name}
                </option>
              ))}
            </select>
          </div>
          <button
            type="submit"
            className="w-full bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
          >
            Изменить
          </button>
        </form>
      </div>
    </>
  );
}
