import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ReactModal from 'react-modal';
import NewReport from './NewReport';

export default function Templates() {
  const [services, setServices] = useState([]);
  const [selectedServices, setSelectedServices] = useState([]);
  const [creatingTemplate, setCreatingTemplate] = useState(false);
  const [templates, setTemplates] = useState([]);
  const [templateName, setTemplateName] = useState('');
  const [isReportModalOpen, setIsReportModalOpen] = useState(false);
  const [selectedTemplate, setSelectedTemplate] = useState(null);

  const formatTemplateDate = (date) => {
    const templateDate = new Date(date);
    const formattedDate = templateDate.toLocaleDateString();
    const formattedTime = templateDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    return `${formattedDate} ${formattedTime}`;
  };

  useEffect(() => {
    fetchServices();
    fetchTemplates();
  }, []);

  const fetchServices = async () => {
    try {
      const response = await request('get', '/services');
      setServices(response.data);
    } catch (error) {
      toast.error('Не удалось получить список услуг.');
    }
  };

  const fetchTemplates = async () => {
    try {
      const response = await request('get', '/template');
      setTemplates(response.data);
    } catch (error) {
      toast.error('Не удалось получить список шаблонов.');
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
    const allServiceIds = services.map((service) => service.id);
    setSelectedServices(allServiceIds);
  };

  const handleDeselectAllServices = () => {
    setSelectedServices([]);
  };

  const createTemplate = async () => {
    try {
      setCreatingTemplate(true);
      const response = await request('post', '/template', { name: templateName });
      const template = response.data;

      for (const service of selectedServices) {
        const templateData = {
          template,
          service,
        };

        await request('post', '/template/data/create', templateData);
      }

      toast.success('Шаблон успешно создан.');
      fetchTemplates();
      setCreatingTemplate(false);
      setTemplateName('');
      setSelectedServices([]);
    } catch (error) {
      toast.error('Не удалось создать шаблон.');
      setCreatingTemplate(false);
    }
  };

  const handleCreateReport = (template) => {
    setSelectedTemplate(template);
    setIsReportModalOpen(true);
  };

  const closeReportModal = () => {
    setSelectedTemplate(null);
    setIsReportModalOpen(false);
  };

  return (
    <>
      <div className="mx-auto p-4">
        <div className="p-2 shadow-lg border">
          <h2 className="text-xl font-bold mb-4">Создание шаблона</h2>
          <form>
            <input
              type="text"
              value={templateName}
              onChange={(e) => setTemplateName(e.target.value)}
              placeholder="Введите название шаблона"
              className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2 mb-2"
              required
            />
            <h2 className="text-md font-bold mb-4">Услуги</h2>
            <div className="space-y-2 mb-4 max-w-7xl max-h-96 overflow-y-auto">
              <div className="flex items-center">
                <button
                  type="button"
                  onClick={handleSelectAllServices}
                  className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none mr-2"
                >
                  Выбрать все
                </button>
                <button
                  type="button"
                  onClick={handleDeselectAllServices}
                  className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
                >
                  Снять выбор
                </button>
              </div>
              {services.map((service, index) => (
                <div
                  key={service.id}
                  className={`flex items-center ${index % 2 === 0 ? 'bg-white' : 'bg-gray-200'}`}
                >
                  <input
                    type="checkbox"
                    value={service.id}
                    checked={selectedServices.includes(service.id)}
                    onChange={(event) => handleServiceSelection(event, service.id)}
                    className="form-checkbox mr-2"
                  />
                  <label className="text-gray-700">{`${index + 1}. ${service.name}`}</label>
                </div>
              ))}
            </div>
            <button
              type="button"
              onClick={createTemplate}
              disabled={creatingTemplate}
              className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
            >
              {creatingTemplate ? 'Создание...' : 'Создать шаблон'}
            </button>
          </form>
        </div>
        <div className="mt-8">
          <h2 className="text-xl font-bold mb-4">Список шаблонов</h2>
          <div className="space-y-4">
            {templates.map((template, index) => (
              <div
                key={template.id}
                className={`bg-gray-100 shadow-md rounded p-4 flex items-center justify-between ${
                  index % 2 === 0 ? 'bg-white' : 'bg-gray-200'
                }`}
              >
                <div className="flex">
                  <div className="w-32">{formatTemplateDate(template.date)}</div>
                  <div className="ml-4 font-semibold">{template.name}</div>
                </div>
                <button
                  onClick={() => handleCreateReport(template)}
                  className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
                >
                  Создать отчет
                </button>
              </div>
            ))}
          </div>
        </div>
      </div>
      <ReactModal
        isOpen={isReportModalOpen}
        onRequestClose={closeReportModal}
        contentLabel="Создание отчета"
        className="Modal"
        overlayClassName="Overlay"
      >
        <NewReport template={selectedTemplate} closeModal={closeReportModal} />
      </ReactModal>
    </>
  );
}
