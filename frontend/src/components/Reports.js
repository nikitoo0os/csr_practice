import React, { useState } from 'react';
import Templates from './Templates';

export default function Report() {
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
          Шаблоны
        </button>
        <button
          className={`py-2 px-4 text-sm font-medium ${activeTab === 1 ? 'text-blue-500 border-b-2 border-blue-500' : 'text-gray-700'
            }`}
          onClick={() => handleTabClick(1)}
        >
          Справочник услуг
        </button>
        <button
          className={`py-2 px-4 text-sm font-medium ${activeTab === 2 ? 'text-blue-500 border-b-2 border-blue-500' : 'text-gray-700'
            }`}
          onClick={() => handleTabClick(2)}
        >
          Tab 3
        </button>
      </div>

      {activeTab === 0 &&
        <div className="p-4">
          <Templates/>
        </div>}

      {activeTab === 1 &&
        <div className="p-4">
          Content for Tab 2
        </div>}

      {activeTab === 2 && <div className="p-4">Content for Tab 3</div>}
    </div>
  );
}
