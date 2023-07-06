import React, { useState } from 'react';
import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom';
import { Navbar, onLogin } from './Navbar';
import Login  from './Login'
import { ToastContainer } from 'react-toastify';
import { getAuthToken } from '../helpers/axios_helper'
import ReactModal from "react-modal";

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(getAuthToken() !== null && getAuthToken() !== "null");
    console.log("appjs " + isAuthenticated);

    const handleLogin = (e, email, password) => {
        onLogin(e, email, password);
    };

    return (<>
        <BrowserRouter>
            <Navbar isAuthenticated={isAuthenticated} />
            <div className="bg-gray-300 pb-96 py-8">
            <Routes>
                <Route path="/login" element={<Login onLogin={handleLogin}/>} />
            </Routes>
            </div>
        </BrowserRouter>
        <ToastContainer />
    </>

    );
}

export default App;