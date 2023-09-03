import React, { useEffect, useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Navbar } from './Navbar';
import Login from './Login'
import { ToastContainer } from 'react-toastify';
import { getAuthToken, decodeJwt } from '../helpers/axios_helper'
import Users from './Users';
import MyReports from './MyReports';
import TemplatesAndReports from './TemplatesAndReports';
import FillReport from './FillReport';
import ReadReport from './ReadReport';
import EditTemplate from './EditTemplate';
import SummaryRepRead from './SummaryRepRead';


function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(getAuthToken() !== null && getAuthToken() !== "null");
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    if (isAuthenticated) {
      setIsAdmin(decodeJwt().isAdmin);
    }
  }, [isAuthenticated]);

  return (
    <>
      <BrowserRouter>
        <Navbar isAuthenticated={isAuthenticated} isAdmin={isAdmin} />
        <div className="bg-gray-300 h-full min-h-screen">
          <Routes>
            {isAuthenticated ? (
              <>
                {isAdmin ? (
                  <>
                    <Route path="/users" element={<Users />} />
                    <Route path="/" element={<TemplatesAndReports />} />
                    <Route path="/edit-template" element={<EditTemplate/>}/>
                    <Route path="/summary-report" element={<SummaryRepRead/>}/>
                  </>
                ) : (
                  <>
                    <Route path="/" element={<MyReports />} />
                    <Route path="/fill-report" element={<FillReport/>} />
                  </>
                )}
                <Route path="/read-report" element={<ReadReport/>} />
              </>
            ) : (
              <>
                <Route path="/" element={<Login />} />
              </>
            )}
          </Routes>
        </div>
      </BrowserRouter>
      <ToastContainer />
    </>
  );
}

export default App;
