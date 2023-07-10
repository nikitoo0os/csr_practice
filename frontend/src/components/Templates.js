import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function Templates() {
  const [services, setServices] = useState([]);
  const [selectedServices, setSelectedServices] = useState([]);
  const [creatingTemplate, setCreatingTemplate] = useState(false);
  const [templates, setTemplates] = useState([]);
  const [templateName, setTemplateName] = useState('');


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
      setSelectedServices(selectedServices.filter(id => id !== service));
    }
  };

  const createTemplate = async () => {
    try {
      setCreatingTemplate(true);
      const response = await request('post', '/template', { name: templateName });
      const template = response.data;
  
      const templateData = selectedServices.map(service => ({
        template,
        service,
      }));
  
      await request('post', '/template/data/create', templateData);
      toast.success('Шаблон успешно создан.');
      fetchTemplates();
      setCreatingTemplate(false);
      setTemplateName(''); // Очистить поле ввода названия шаблона
    } catch (error) {
      toast.error('Не удалось создать шаблон.');
      setCreatingTemplate(false);
    }
  };
  
  const handleCreateReport = (templateId) => {
    // Обработка создания отчета на основе выбранного шаблона
    console.log(`Создание отчета для шаблона с ID: ${templateId}`);
  };

  return (
    <>
      <div className="mx-auto p-4">
        <h1 className="text-2xl font-bold mb-4">Шаблоны</h1>
        <form>
          <input
            type="text"
            value={templateName}
            onChange={(e) => setTemplateName(e.target.value)}
            placeholder="Введите название шаблона"
            className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2 mb-2"
          />

          <div className="space-y-2 mb-4 max-w-5xl max-h-96 overflow-y-auto">
            {services.map(service => (
              <div key={service.id} className="flex items-center">
                <input
                  type="checkbox"
                  value={service}
                  checked={selectedServices.includes(service)}
                  onChange={event => handleServiceSelection(event, service)}
                  className="form-checkbox mr-2"
                />
                <label className="text-gray-700">{service.name}</label>
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
        <div className="mt-8">
          <h2 className="text-xl font-bold mb-4">Список шаблонов</h2>
          <div className="space-y-4">
            {templates.map(template => (
              <div key={template.id} className="bg-gray-100 shadow-md rounded p-4 flex items-center justify-between">
                <span>{formatTemplateDate(template.date)}</span>
                <button
                  onClick={() => handleCreateReport(template.id)}
                  className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
                >
                  Создать отчет
                </button>
              </div>
            ))}
          </div>
        </div>
      </div>
    </>
  );
}
