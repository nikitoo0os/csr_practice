import React, { useEffect, useState } from "react";
import { request } from "../helpers/axios_helper";
import { toast } from "react-toastify";
import Select from 'react-select';

export default function NewReport({ template, closeModal }) {
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [comment, setComment] = useState("");
  const [regions, setRegions] = useState([]);
  const [selectedRegion, setSelectedRegion] = useState('');
  const [selectAllRegions, setSelectAllRegions] = useState(false);


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

  const selectStyles = {
    control: (provided, state) => ({
      ...provided,
      maxHeight: '200px',
    }),
    menu: provided => ({
      ...provided,
      maxHeight: '150px',
    }),
    menuList: provided => ({
      ...provided,
      maxHeight: '150px',
    }),
    multiValue: provided => ({
      ...provided,
      maxWidth: '80%',
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    }),
    valueContainer: provided => ({
      ...provided,
      flexWrap: 'wrap',
      maxHeight: '80px',
      overflowY: 'auto',
    }),
  };


  const handleSubmit = async (event) => {
    event.preventDefault();

    if (selectedRegion.length === 0) {
      toast.error("Выберите хотя бы один регион.");
      return;
    }

    try {
      const selectedRegionsData = selectedRegion.map(regionOption => {
        return regions.find(region => region.id === regionOption.value);
      });

      const requestData = {
        template,
        startDate,
        endDate,
        comment,
        regions: selectedRegionsData,
      };
      await request("post", "/reports", requestData);
      toast.success("Отчет успешно создан.");
      closeModal();
    } catch (error) {
      toast.error("Не удалось создать отчет.");
    }
  };



  const handleStartDateChange = (event) => {
    const selectedStartDate = event.target.value;
    if (endDate === '') {
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
          <label className="block font-bold mb-2">Дата начала отчета:</label>
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
          <label className="block font-bold mb-2">Дата окончания отчета:</label>
          <input
            type="date"
            value={endDate}
            onChange={handleEndDateChange}
            min={startDate}
            className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
            required
          />
        </div>

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
          <Select
            options={regions.map(region => ({
              value: region.id,
              label: region.name
            }))}
            value={selectedRegion}
            onChange={selectedOptions => setSelectedRegion(selectedOptions)}
            isMulti
            className="w-full"
            required
            placeholder="Выберите регионы"
            styles={selectStyles}
          />
          <button
            className="text-blue-600 underline focus:outline-none"
            onClick={(event) => {
              event.preventDefault(); // Предотвращаем отправку формы
              if (selectAllRegions) {
                setSelectedRegion([]);
              } else {
                setSelectedRegion(
                  regions.map(region => ({
                    value: region.id,
                    label: region.name
                  }))
                );
              }
              setSelectAllRegions(!selectAllRegions);
            }}
          >
            {selectAllRegions ? "Отменить выбор всех регионов" : "Выбрать все регионы"}
          </button>

        </div>
        <button
          type="submit"
          className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
        >
          Создать
        </button>
      </form>
    </div>
  );
}