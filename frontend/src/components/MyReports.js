import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ReactModal from "react-modal";

export default function MyReports() {
    const [activeTab, setActiveTab] = useState(0);
  
    const handleTabClick = (index) => {
      setActiveTab(index);
    };
  
    return (
      <div className="bg-white shadow-md mx-auto max-w-screen-xl">
        <div className="flex">
          <button
            className={`py-2 px-4 text-sm font-medium ${activeTab === 0 ? 'text-blue-500 border-b-2 border-blue-500' : 'text-gray-700'
              }`}
            onClick={() => handleTabClick(0)}
          >
            Активные отчеты
          </button>
          <button
            className={`py-2 px-4 text-sm font-medium ${activeTab === 1 ? 'text-blue-500 border-b-2 border-blue-500' : 'text-gray-700'
              }`}
            onClick={() => handleTabClick(1)}
          >
            Завершенные отчеты
          </button>
        </div>
  
        {activeTab === 0 &&
          <div className="p-4">
            Активные
          </div>}
  
        {activeTab === 1 &&
          <div className="p-4">
            Завершенные
          </div>}
      </div>
    );
  }
  