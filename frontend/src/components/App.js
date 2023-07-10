import React, { useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Navbar } from './Navbar';
import Login  from './Login'
import { ToastContainer } from 'react-toastify';
import { getAuthToken } from '../helpers/axios_helper'
import Users from './Users';
import MyReports from './MyReports';
import TemplatesAndReports from './TemplatesAndReports';
import NewReport from './NewReport';

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(getAuthToken() !== null && getAuthToken() !== "null");

    return (<>
        <BrowserRouter>
            <Navbar isAuthenticated={isAuthenticated} />
            <div className="bg-gray-300 h-full min-h-screen">
            <Routes>
                {isAuthenticated ? (<>
                </>):(<>
                    <Route path="/" element={<Login/>} />
                </>)}
                <Route path="/users" element={<Users/>} />
                <Route path="/reports" element={<TemplatesAndReports/>} />
                <Route path="/myreports" element={<MyReports/>} />
                <Route path="/newreport" element={<NewReport/>} />
            </Routes>
            </div>
        </BrowserRouter>
        <ToastContainer />
    </>

    );
}

export default App;