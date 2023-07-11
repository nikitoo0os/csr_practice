import React, { useEffect, useState } from "react";
import { request } from "../helpers/axios_helper";
import { toast } from "react-toastify";

export default function NewReport({ template, closeModal }) {
  const [isRecurring, setIsRecurring] = useState(false);
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [activeDays, setActiveDays] = useState("");
  const [comment, setComment] = useState("");
  const [frequency, setFrequency] = useState("");
  const [regions, setRegions] = useState([]);
  const [selectedRegion, setSelectedRegion] = useState("");

  useEffect(() => {
    fetchRegions();
  }, []);

  const fetchRegions = async () => {
    try {
      const response = await request("get", "/regions");
      setRegions(response.data);
    } catch (error) {
      toast.error("Не удалось получить список регионов.");
    }
  };

  const handleRecurringChange = (event) => {
    setIsRecurring(event.target.value === "recurring");
  };

  const handleRegionChange = (event) => {
    console.log(event.target.value);
    setSelectedRegion(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    // Отправка данных отчета на сервер
    try {
      let requestData = {};

      if (!isRecurring) {
        requestData = {
          template,
          startDate,
          endDate,
          comment,
          regionId: selectedRegion,
        };
      } else {
        requestData = {
          template,
          activeDays: activeDays ? Number(activeDays) : null,
          frequency:
            frequency === "day"
              ? 24 * 60 * 60 * 1000 // Временной интервал в миллисекундах для дней
              : frequency === "week"
                ? 7 * 24 * 60 * 60 * 1000 // Временной интервал в миллисекундах для недель
                : frequency === "month"
                  ? 30 * 24 * 60 * 60 * 1000 // Временной интервал в миллисекундах для месяцев
                  : frequency === "year"
                    ? 365 * 24 * 60 * 60 * 1000 // Временной интервал в миллисекундах для лет
                    : null,
          comment,
          regionId: selectedRegion,
        };
      }

      console.log(requestData);

      const response = await request("post", "/reports", requestData);
      // Обработка успешного создания отчета
      toast.success("Отчет успешно создан.");
      // Сброс значений полей формы
      setIsRecurring(false);
      setStartDate("");
      setEndDate("");
      setActiveDays("");
      setComment("");
      setFrequency("");
      setSelectedRegion("");
      closeModal();
    } catch (error) {
      // Обработка ошибки при создании отчета
      toast.error("Не удалось создать отчет.");
    }
  };

  const handleStartDateChange = (event) => {
    const selectedStartDate = event.target.value;
    console.log(endDate);
    if (endDate === '')
    {
      setStartDate(selectedStartDate);
    }
    else if (selectedStartDate >= endDate) {
      toast.error("Дата начала отчета не может быть позднее даты окончания.");
    } else {
      setStartDate(selectedStartDate);
    }
  };

  const handleEndDateChange = (event) => {
    const selectedEndDate = event.target.value;
    if (startDate >= selectedEndDate) {
      toast.error("Дата начала отчета не может быть позднее даты окончания.");
    } else {
      setEndDate(selectedEndDate);
    }
  };

  return (
    <>
      <div className="mx-auto w-1/4 bg-white p-4 mt-60">
        <div className="flex justify-end">
          <button onClick={() => closeModal()}>
            <img
              className="h-6 md:border-0 hover:brightness-90"
              src={require("../pictures/close.png")}
              alt="Войти"
            />
          </button>
        </div>
        <h1 className="text-2xl font-bold mb-4">Создание отчета</h1>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block font-bold mb-2">Тип отчета:</label>
            <div>
              <label className="mr-4">
                <input
                  type="radio"
                  value="one-time"
                  checked={isRecurring === false}
                  onChange={handleRecurringChange}
                  className="mr-2"
                />
                Разовый
              </label>
              <label>
                <input
                  type="radio"
                  value="recurring"
                  checked={isRecurring === true}
                  onChange={handleRecurringChange}
                  className="mr-2"
                />
                Периодичный
              </label>
            </div>
          </div>

          {!isRecurring ? (
            <>
              <div className="mb-4">
                <label className="block font-bold mb-2">
                  Дата начала отчета:
                </label>
                <input
                  type="date"
                  value={startDate}
                  onChange={handleStartDateChange}
                  min={new Date().toISOString().split("T")[0]}
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
                  required
                />
              </div>

              <div className="mb-4">
                <label className="block font-bold mb-2">
                  Дата окончания отчета:
                </label>
                <input
                  type="date"
                  value={endDate}
                  onChange={handleEndDateChange}
                  min={startDate}
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
                  required
                />
              </div>
            </>
          ) : (
            <>
              <div className="mb-4">
                <label className="block font-bold mb-2">Периодичность:</label>
                <select
                  value={frequency}
                  onChange={(e) => setFrequency(e.target.value)}
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
                  required
                >
                  <option value="">Выберите периодичность</option>
                  <option value="day">День</option>
                  <option value="week">Неделя</option>
                  <option value="month">Месяц</option>
                  <option value="year">Год</option>
                </select>
              </div>
              <div className="mb-4">
                <label className="block font-bold mb-2">
                  Продолжительность в днях:
                </label>
                <input
                  type="number"
                  value={activeDays}
                  min={1}
                  onChange={(e) => setActiveDays(e.target.value)}
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
                  required
                />
              </div>
            </>
          )}
          <div className="mb-4">
            <label className="block font-bold mb-2">Комментарий:</label>
            <textarea
              value={comment}
              onChange={(e) => setComment(e.target.value)}
              placeholder="Введите комментарий"
              className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2 max-h-48"
            ></textarea>
          </div>
          <div className="mb-4">
            <label className="block font-bold mb-2">Регион:</label>
            <select
              value={selectedRegion}
              onChange={handleRegionChange}
              className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
              required
            >
              <option value="">Выберите регион</option>
              {regions.map((region) => (
                <option key={region.id} value={region.id}>
                  {region.name}
                </option>
              ))}
            </select>
          </div>
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
