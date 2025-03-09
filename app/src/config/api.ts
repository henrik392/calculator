/**
 * API configuration settings
 */

import router from '@/router';
import { useAuthStore } from '@/stores/auth';
import axios from 'axios';

export const API_CONFIG = {
    BASE_URL: 'http://localhost:8080',
    API_BASE_URL:
        import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
    DEFAULT_PAGE_SIZE: 5,
    TIMEOUT: 10000, // 10 seconds
};

const api = axios.create({
    baseURL: API_CONFIG.API_BASE_URL,
    timeout: API_CONFIG.TIMEOUT,
    headers: {
        'Content-Type': 'application/json',
    },
});

api.interceptors.request.use(
    (config) => {
        const authStore = useAuthStore();
        if (authStore.token) {
            config.headers.Authorization = `Bearer ${authStore.token}`;
        }

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            const authStore = useAuthStore();
            authStore.logout();
            // Redirect to login page
            router.push('/login');
        }
        return Promise.reject(error);
    }
);

/**
 * Helper function to build API URLs with proper path handling
 */
export const buildApiUrl = (
    path: string,
    queryParams?: Record<string, string | number>
): URL => {
    const url = new URL(
        `${API_CONFIG.API_BASE_URL}/${path.replace(/^\//, '')}`
    );

    if (queryParams) {
        Object.entries(queryParams).forEach(([key, value]) => {
            url.searchParams.append(key, String(value));
        });
    }

    return url;
};

export default api;
