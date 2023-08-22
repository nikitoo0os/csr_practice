import React, { useEffect, useState, useCallback } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';
import { useLocation } from 'react-router-dom';

export default function ReadReport() {
  const [reportsData, setReportsData] = useState([]);
  const location = useLocation();
  const report = location.state && location.state.report;
  const [serviceNameFilter, setServiceNameFilter] = useState('');
  const [count1Filter, setCount1Filter] = useState('');
  const [count2Filter, setCount2Filter] = useState('');
  const [percent1Filter, setPercent1Filter] = useState('');
  const [percent2Filter, setPercent2Filter] = useState('');
  const [regularActFilter, setRegularActFilter] = useState('');
  const [filteredData, setFilteredData] = useState(reportsData);

  useEffect(() => {
    fetchReportData();
  }, []);

  const fetchReportData = useCallback(async () => {
    try {
      const response = await request('get', `/report/data/services/${report.id}`);
      setReportsData(response.data);
    } catch (error) {
      toast.error('Не удалось получить услуги отчета.');
    }
  }, [report.id]);

  // Calculate the total count1 and count2
  const totalCount1 = reportsData.reduce((acc, item) => acc + (parseFloat(item.count1) || 0), 0);
  const totalCount2 = reportsData.reduce((acc, item) => acc + (parseFloat(item.count2) || 0), 0);

  // Calculate the percent1 for the total row
  const totalPercent1 = totalCount1 !== 0 ? ((totalCount2 * 100) / totalCount1).toFixed(1) : '';

  useEffect(() => {
    filterData();
  }, [reportsData, serviceNameFilter, count1Filter, count2Filter, percent1Filter, percent2Filter, regularActFilter]);

  const filterData = () => {
    const filtered = reportsData.filter((item) => {
      const serviceNameMatch = !serviceNameFilter || item.service.name?.toLowerCase().includes(serviceNameFilter.toLowerCase());
      const count1Match = !count1Filter || item.count1?.toString() === count1Filter;
      const count2Match = !count2Filter || item.count2?.toString() === count2Filter;
      const percent1Match = !percent1Filter || item.percent1?.toString() === percent1Filter;
      const percent2Match = !percent2Filter || item.percent2?.toString() === percent2Filter;
      const regularActMatch = !regularActFilter || item.regularAct?.toLowerCase().includes(regularActFilter.toLowerCase());
  
      return serviceNameMatch && count1Match && count2Match && percent1Match && percent2Match && regularActMatch;
    });
  
    setFilteredData(filtered);
  
    // const updatedFormData = filtered.map((item) => {
    //   const existingFormEntry = formData.find((formItem) => formItem.id === item.id);
    //   if (existingFormEntry) {
    //     return existingFormEntry;
    //   }
    //   return {
    //     id: item.id,
    //     service: item.service,
    //     count1: item.count1,
    //     count2: item.count2,
    //     percent1: item.percent1,
    //     percent2: item.percent2,
    //     regularAct: item.regularAct,
    //   };
    // });
  
    // setFormData(updatedFormData);
  };
 

  return (
    <>
      <div className="bg-white p-4">
        <table className="table-auto w-full border shadow-lg">
          <thead>
            <tr className="bg-sky-600 text-white">
              <th className="px-4 py-2 border w-10">№</th>
              <th className="px-4 py-2 border w-96">Наименование услуги в Кировской области</th>
              <th className="px-4 py-2 border w-64">{report.template.countAllRequests}</th>
              <th className="px-4 py-2 border w-64">{report.template.countEPGURequests}</th>
              <th className="px-4 py-2 border w-64">{report.template.percentEPGURequests}</th>
              <th className="px-4 py-2 border w-64">{report.template.percentNotViolationEPGURequests}</th>
              <th className="px-4 py-2 border w-96">Наименование муниципального нормативного акта (административного регламента), которые приведены в соответствие с описанием целевого состояния (ОЦС) или типовым федеральным регламентом (номер и дата документа)</th>
            </tr>
            <tr className="bg-sky-600">
              <th className="px-4 py-2 border w-10"></th>
              <th className="px-4 py-2 border w-96">
                <input
                  type="text"
                  placeholder="Фильтр"
                  value={serviceNameFilter}
                  onChange={(e) => {setServiceNameFilter(e.target.value);
                    filterData();}}
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                />
              </th>
              <th className="px-4 py-2 border w-96">
                <input
                  type="number"
                  placeholder="Фильтр"
                  value={count1Filter}
                  onChange={(e) => {setCount1Filter(e.target.value);
                    filterData();}}
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                />
              </th>
              <th className="px-4 py-2 border w-96">
                <input
                  type="number"
                  placeholder="Фильтр"
                  value={count2Filter}
                  onChange={(e) => {setCount2Filter(e.target.value);
                    filterData();}}
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                />
              </th>
              <th className="px-4 py-2 border w-96">
                <input
                  type="number"
                  placeholder="Фильтр"
                  value={percent1Filter}
                  onChange={(e) => {setPercent1Filter(e.target.value);
                    filterData();}}
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                />
              </th>
              <th className="px-4 py-2 border w-96">
                <input
                  type="number"
                  placeholder="Фильтр"
                  value={percent2Filter}
                  onChange={(e) => {setPercent2Filter(e.target.value);
                    filterData();}}
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                />
              </th>
              <th className="px-4 py-2 border w-96">
                <input
                  type="text"
                  placeholder="Фильтр"
                  value={regularActFilter}
                  onChange={(e) => {setRegularActFilter(e.target.value);
                    filterData();}}
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-2 py-1"
                />
              </th>
            </tr>
          </thead>
          <tbody>
            {filteredData.map((reportData, index) => (
              <tr key={reportData.service.id}>
                <td className="border px-4 py-2">{index + 1}</td>
                <td className="border px-4 py-2">{reportData.service.name}</td>
                <td className="border px-4 py-2">{reportData.count1}</td>
                <td className="border px-4 py-2">{reportData.count2}</td>
                <td className="border px-4 py-2">{reportData.percent1}</td>
                <td className="border px-4 py-2">{reportData.percent2}</td>
                <td className="border px-4 py-2">{reportData.regularAct}</td>
              </tr>
            ))}
            <tr className="bg-gray-200">
              <td className="border border-gray-300 px-4 py-2 text-right font-semibold" colSpan="2">
                ИТОГО:
              </td>
              <td className="border border-gray-300 px-4 py-2 font-semibold">{totalCount1}</td>
              <td className="border border-gray-300 px-4 py-2 font-semibold">{totalCount2}</td>
              <td className="border border-gray-300 px-4 py-2 font-semibold">{totalPercent1}</td>
              <td className="border border-gray-300 px-4 py-2"></td>
              <td className="border border-gray-300 px-4 py-2"></td>
            </tr>
          </tbody>
        </table>
      </div>
    </>
  );
}
