import React, { useEffect, useState } from "react";
import { request } from "../helpers/axios_helper";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ReactModal from "react-modal";

export default function NewTemplate() {
  const [services, setServices] = useState([]);
  const [selectedServices, setSelectedServices] = useState([]);
  const [creatingTemplate, setCreatingTemplate] = useState(false);
  const [templateName, setTemplateName] = useState("");
  const [serviceFilter, setServiceFilter] = useState("");
  const [column1, setColumn1] = useState("");
  const [column2, setColumn2] = useState("");
  const [column3, setColumn3] = useState("");
  const [column4, setColumn4] = useState("");

  useEffect(() => {
    fetchServices();
  }, []);

  const fetchServices = async () => {
    try {
      const response = await request("get", "/services");
      setServices(response.data);
    } catch (error) {
      toast.error("Не удалось получить список услуг.");
    }
  };

  const handleServiceSelection = (event, service) => {
    if (event.target.checked) {
      setSelectedServices([...selectedServices, service]);
    } else {
      setSelectedServices(selectedServices.filter((id) => id !== service));
    }
  };

  const handleSelectAllServices = () => {
    const allServiceIds = services.map((service) => service);
    setSelectedServices(allServiceIds);
  };

  const handleDeselectAllServices = () => {
    setSelectedServices([]);
  };

  const createTemplate = async (e) => {
    e.preventDefault();
    try {
      if (selectedServices.length > 0) {
        setCreatingTemplate(true);
        const response = await request("post", "/template", {
          name: templateName,
          countAllRequests: column1,
          countEPGURequests: column2,
          percentEPGURequests: column3,
          percentNotViolationEPGURequests: column4,
        });
        const template = response.data;

        for (const service of selectedServices) {
          const templateData = {
            template,
            service,
          };

          await request("post", "/template/data/create", templateData);
        }

        toast.success("Шаблон успешно создан.");
        setCreatingTemplate(false);
        setTemplateName("");
        setColumn1("");
        setColumn2("");
        setColumn3("");
        setColumn4("");
        setSelectedServices([]);
      } else {
        toast.error(
          "Необходимо выбрать хотя бы одну услугу для создания шаблона"
        );
      }
    } catch (error) {
      toast.error("Не удалось создать шаблон.");
      setCreatingTemplate(false);
    }
  };

  return (
    <>
      <div className="mx-auto p-4">
        <div>
          <h2 className="text-xl font-bold mb-4">Создание шаблона</h2>
          <form onSubmit={createTemplate}>
            <label>Название шаблона</label>
            <input
              type="text"
              value={templateName}
              onChange={(e) => setTemplateName(e.target.value)}
              placeholder="Введите название шаблона"
              className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2 mb-2"
              required
            />
            <h2>Услуги</h2>
            <div className="border-2 rounded p-2 mb-4 shadow-lg">
              <div className="border-2 w-full p-2 mb-2 mt-1 shadow-lg rounded">
                <h2 className="font-semibold border-b-2">Фильтр</h2>
                <div className="flex flex-col mt-4">
                  <label className="text-sm mb-1">Название услуги</label>
                  <input
                    type="text"
                    value={serviceFilter}
                    onChange={(e) => setServiceFilter(e.target.value)}
                    placeholder="Введите название услуги"
                    className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2 mb-2"
                  />
                </div>
              </div>
              <div className="space-y-2 max-w-7xl max-h-96 overflow-y-auto border shadow-lg p-2">
                {services
                  .filter((service) =>
                    service.name
                      .toLowerCase()
                      .includes(serviceFilter.toLowerCase())
                  )
                  .map((service, index) => (
                    <div
                      key={service.id}
                      className={`flex items-center ${
                        index % 2 === 0 ? "bg-white" : "bg-gray-200"
                      }`}
                    >
                      <input
                        type="checkbox"
                        value={service}
                        checked={selectedServices.includes(service)}
                        onChange={(event) =>
                          handleServiceSelection(event, service)
                        }
                        className="form-checkbox mr-2"
                      />
                      <label className="text-gray-700">{`${index + 1}. ${
                        service.name
                      }`}</label>
                    </div>
                  ))}
              </div>

              <div className="flex items-center mb-1 border border-gray-300 rounded bg-gray-200 shadow-lg p-1">
                <button
                  type="button"
                  onClick={handleSelectAllServices}
                  className="text-blue-600 underline ml-2 focus:outline-none"
                >
                  Выбрать все
                </button>
                <button
                  type="button"
                  onClick={handleDeselectAllServices}
                  className="text-blue-600 underline ml-4 focus:outline-none"
                >
                  Снять выбор
                </button>
              </div>
            </div>
            <div>
              <label>Название первого столбца для заполнения</label>
              <textarea
                value={column1}
                onChange={(e) => setColumn1(e.target.value)}
                className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2 mb-2 max-h-32"
                required
              />
              <label>Название второго столбца для заполнения</label>
              <textarea
                value={column2}
                onChange={(e) => setColumn2(e.target.value)}
                className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2 mb-2 max-h-32"
                required
              />
              <label>Название третьего столбца для заполнения</label>
              <textarea
                value={column3}
                onChange={(e) => setColumn3(e.target.value)}
                className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2 mb-2 max-h-32"
                required
              />
              <label>Название четвертого столбца для заполнения</label>
              <textarea
                value={column4}
                onChange={(e) => setColumn4(e.target.value)}
                className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2 mb-2 max-h-32"
                required
              />
            </div>

            <button
              type="submit"
              disabled={creatingTemplate}
              className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
            >
              {creatingTemplate ? "Создание..." : "Создать шаблон"}
            </button>
          </form>
        </div>
      </div>
    </>
  );
}
