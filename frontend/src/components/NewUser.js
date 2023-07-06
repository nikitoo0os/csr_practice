import React, { useState } from 'react';
import { toast } from 'react-toastify';

export default function NewUser() {
  const [surname, setSurname] = useState('');
  const [firstname, setFirstname] = useState('');
  const [patronymic, setPatronymic] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [region, setRegion] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();

    // Ваш код для отправки данных формы на сервер

    // Очищаем значения полей формы
    setSurname('');
    setFirstname('');
    setPatronymic('');
    setEmail('');
    setPassword('');
    setRegion('');

    toast.success('Пользователь успешно добавлен');
  };

  return (
    <>
      <div className="w-1/4 bg-white rounded p-4">
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
              Email
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
              value={region}
              onChange={(e) => setRegion(e.target.value)}
              className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
              required
            >
              <option value="">Выберите район</option>
            </select>
          </div>
          <button
            type="submit"
            className="w-full bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none"
          >
            Добавить
          </button>
        </form>
      </div>
    </>
  );
}
