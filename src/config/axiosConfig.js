// src/config/axiosConfig.js
import axios from 'axios';

const instance = axios.create({
    withCredentials: true,
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
    return !!token; // return true of token is not null or undefined
};




let csrfToken = null;
export const fetchCsrfToken = async () => {
    try {
        const response = await instance.get('/csrf-token');
        csrfToken = response.data.token;
    } catch (error) {
        console.error('Error fetching CSRF token:', error);
    }
};


instance.interceptors.request.use((config) => {
    if (csrfToken) {
        config.headers['X-XSRF-TOKEN'] = csrfToken;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

export default instance;
