// src/config/axiosConfig.js
import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8081/api/v1',
    timeout: 100000,
    headers: {
        'Content-Type': 'application/json',
    },
});

instance.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

export const isAuthenticated = () => {
    const token = localStorage.getItem('token');
    return !!token; // Retourne true si un token est présent, false sinon
};

export default instance;
