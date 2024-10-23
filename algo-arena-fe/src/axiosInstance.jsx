import axios from "axios";

const API_BASE_URI = import.meta.env.VITE_ALGO_ARENA_BASE_URI || 'http://localhost:8080';

const axiosInstance = axios.create({
    baseURL: API_BASE_URI,
});

export default axiosInstance;

