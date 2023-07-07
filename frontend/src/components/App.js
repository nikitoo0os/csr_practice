import React, { useState } from 'react';
import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom';
import { Navbar, onLogin } from './Navbar';
import Login  from './Login'
import { ToastContainer } from 'react-toastify';
import { getAuthToken } from '../helpers/axios_helper'
import ReactModal from "react-modal";
import Users from './Users';
import NewUser from './NewUser';
import Report from './Reports';

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(getAuthToken() !== null && getAuthToken() !== "null");
    const handleLogin = (e, email, password) => {
        onLogin(e, email, password);
    };

    return (<>
        <BrowserRouter>
            <Navbar isAuthenticated={isAuthenticated} />
            <div className="bg-gray-300 h-screen">
            <Routes>
                {isAuthenticated ? (<>
                </>):(<>
                    <Route path="/" element={<Login onLogin={handleLogin}/>} />
                    <Route path="/users" element={<Users/>} />
                    <Route path="/newuser" element={<NewUser/>} />
                    <Route path="/reports" element={<Report/>} />
                </>)}
            </Routes>
            </div>
        </BrowserRouter>
        <ToastContainer />
    </>

    );
}

export default App;