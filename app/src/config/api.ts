/**
 * API configuration settings
 */

export const API_CONFIG = {
    BASE_URL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
    DEFAULT_PAGE_SIZE: 5,
    TIMEOUT: 10000, // 10 seconds
};

/**
 * Helper function to build API URLs with proper path handling
 */
export const buildApiUrl = (
    path: string,
    queryParams?: Record<string, string | number>
): URL => {
    const url = new URL(`${API_CONFIG.BASE_URL}/${path.replace(/^\//, '')}`);

    if (queryParams) {
        Object.entries(queryParams).forEach(([key, value]) => {
            url.searchParams.append(key, String(value));
        });
    }

    return url;
};
