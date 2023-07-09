import React, { useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Navbar } from './Navbar';
import Login  from './Login'
import { ToastContainer } from 'react-toastify';
import { getAuthToken } from '../helpers/axios_helper'
import Users from './Users';
import MyReports from './MyReports';
import TemplatesAndReports from './TemplatesAndReports';

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(getAuthToken() !== null && getAuthToken() !== "null");

    return (<>
        <BrowserRouter>
            <Navbar isAuthenticated={isAuthenticated} />
            <div className="bg-gray-300 h-screen">
            <Routes>
                {isAuthenticated ? (<>
                </>):(<>
                    <Route path="/" element={<Login/>} />
                </>)}
                <Route path="/users" element={<Users/>} />
                <Route path="/reports" element={<TemplatesAndReports/>} />
                <Route path="/myreports" element={<MyReports/>} />
            </Routes>
            </div>
        </BrowserRouter>
        <ToastContainer />
    </>

    );
}

export default App;