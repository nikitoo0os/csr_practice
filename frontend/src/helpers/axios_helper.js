import axios from 'axios';
import jwtDecode from 'jwt-decode';
// Получение jwt из local storage
export const getAuthToken = () => {
    return window.localStorage.getItem('auth_token');
};

// Установка jwt
export const setAuthHeader = (token) => {
    window.localStorage.setItem('auth_token', token);
};


axios.defaults.baseURL = 'http://localhost:8080';
axios.defaults.headers.post['Content-Type'] = 'application/json';


// запрос с валидацией токена на бэкэнде
export const request = (method, url, data) => {

    let headers = {};
    if (getAuthToken() !== null && getAuthToken() !== "null") {
        headers = { 'Authorization': `Bearer ${getAuthToken()}` };
    }

    return axios({
        method: method,
        url: url,
        headers: headers,
        data: data
    });
};

export const decodeJwt = () => {
    try {
        const decoded = jwtDecode(getAuthToken());
        return decoded;
    } catch (error) {
        console.error('Ошибка при декодировании JWT:', error);
        return null;
    }
};