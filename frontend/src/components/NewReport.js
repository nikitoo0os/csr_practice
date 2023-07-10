import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';

export default function NewReport() {
  const [isRecurring, setIsRecurring] = useState(false);
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [activeDays, setActiveDays] = useState('');
  const [comment, setComment] = useState('');
  const [isActive, setIsActive] = useState(false);
  const [frequency, setFrequency] = useState('');

  const handleRecurringChange = (event) => {
    setIsRecurring(event.target.checked);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    // Отправка данных отчета на сервер
    try {
      const response = await request('post', '/reports', {
        isRecurring,
        startDate,
        endDate,
        activeDays,
        comment,
        isActive,
        frequency,
      });
      // Обработка успешного создания отчета
      toast.success('Отчет успешно создан.');
      // Сброс значений полей формы
      setIsRecurring(false);
      setStartDate('');
      setEndDate('');
      setActiveDays('');
      setComment('');
      setIsActive(false);
      setFrequency('');
    } catch (error) {
      // Обработка ошибки при создании отчета
      toast.error('Не удалось создать отчет.');
    }
  };

  return (
    <>
      <div className="mx-auto p-4">
        <h1 className="text-2xl font-bold mb-4">Создание отчета</h1>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block font-bold mb-2">
              <input
                type="checkbox"
                checked={isRecurring}
                onChange={handleRecurringChange}
                className="mr-2"
              />
              Отчет периодичный
            </label>
          </div>

          {!isRecurring && (
            <>
              <div className="mb-4">
                <label className="block font-bold mb-2">Дата начала отчета:</label>
                <input
                  type="date"
                  value={startDate}
                  onChange={(e) => setStartDate(e.target.value)}
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
                />
              </div>

              <div className="mb-4">
                <label className="block font-bold mb-2">Дата окончания отчета:</label>
                <input
                  type="date"
                  value={endDate}
                  onChange={(e) => setEndDate(e.target.value)}
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
                />
              </div>
            </>
          )}

          {isRecurring && (
            <div className="mb-4">
              <label className="block font-bold mb-2">Количество дней активности:</label>
              <input
                type="number"
                value={activeDays}
                onChange={(e) => setActiveDays(e.target.value)}
                className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
              />
            </div>
          )}

          <div className="mb-4">
            <label className="block font-bold mb-2">Комментарий:</label>
            <textarea
              value={comment}
              onChange={(e) => setComment(e.target.value)}
              placeholder="Введите комментарий"
              className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
            ></textarea>
          </div>

          <div className="mb-4">
            <label className="block font-bold mb-2">
              <input
                type="checkbox"
                checked={isActive}
                onChange={(e) => setIsActive(e.target.checked)}
                className="mr-2"
              />
              Активность
            </label>
          </div>

          {isRecurring && (
            <div className="mb-4">
              <label className="block font-bold mb-2">Периодичность:</label>
              <select
                value={frequency}
                onChange={(e) => setFrequency(e.target.value)}
                className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
              >
                <option value="">Выберите периодичность</option>
                <option value="day">День</option>
                <option value="week">Неделя</option>
                <option value="month">Месяц</option>
                <option value="year">Год</option>
              </select>
            </div>
          )}

          <button
            type="submit"
            className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
          >
            Создать отчет
          </button>
        </form>
      </div>
    </>
  );
}
