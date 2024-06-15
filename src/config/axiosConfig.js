import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8081/api/v1', // URL de base correcte pour votre backend
    timeout: 100000,
    headers: {
        'Content-Type': 'application/json',
    },
});

export default instance;
