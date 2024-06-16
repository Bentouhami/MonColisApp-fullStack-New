import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8081/api/v1',
    timeout: 100000,
    headers: {
        'Content-Type': 'application/json',
    },
});

// use this to set the token in local storage for the next request to the server
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

export default instance;
