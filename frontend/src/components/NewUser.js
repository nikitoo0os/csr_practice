import React, { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import { request } from '../helpers/axios_helper'

export default function NewUser({ closeModal, setIsUserAdded }) {
  const [surname, setSurname] = useState('');
  const [firstname, setFirstname] = useState('');
  const [patronymic, setPatronymic] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [region_id, setRegion] = useState('');
  const [regionList, setRegionList] = useState([]);

  useEffect(() => {
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

    fetchRegions();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const userData = {
        surname,
        firstname,
        patronymic,
        email,
        password,
        region_id
      };

      // Отправляем данные на сервер
      const response = await request('post', '/users', userData);
      console.log()

      // Проверяем успешный статус ответа
      if (response.status === 201) {
        // Очищаем значения полей формы
        setSurname('');
        setFirstname('');
        setPatronymic('');
        setEmail('');
        setPassword('');
        setRegion('');
        setIsUserAdded(true);
        toast.success('Пользователь успешно добавлен');
        closeModal();
      }  else {
        // Обработка ошибки, если требуется
      }
    } catch (error) {
      // Обработка ошибки, если требуется
    }
  };

  return (
    <>
      <div className="w-1/4 bg-white rounded p-4 mx-auto mt-56">
        <div className="flex justify-end">
        <button onClick={() => closeModal() }>
          <img
            className="h-6 md:border-0 hover:brightness-90"
            src={require("../pictures/close.png")}
            alt="Войти"
          />
        </button>
        </div>
        <h2 className="text-2xl font-bold text-gray-800 mb-4">Добавить пользователя</h2>
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
            <label className="block text-sm font-medium text-gray-800" htmlFor="password">
              Пароль
            </label>
            <input
              type="password"
              id="password"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
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
            Добавить
          </button>
        </form>
      </div>
    </>
  );
}
