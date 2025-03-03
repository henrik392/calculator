import { API_CONFIG, buildApiUrl } from '../config/api';

interface PaginatedResponse<T> {
    content: T[];
    pageNumber: number;
    pageSize: number;
    totalElements: number;
    totalPages: number;
    isLast: boolean;
    message?: string;
}

interface HistoryItem {
    expression: string;
    result: string;
    timestamp: string;
}

export const historyService = {
    /**
     * Fetch calculation history
     * @param page Page number (zero-based)
     * @param size Items per page
     * @returns History items and pagination info
     */
    async fetchHistory(
        page: number = 0,
        size: number = API_CONFIG.DEFAULT_PAGE_SIZE
    ): Promise<{
        items: HistoryItem[];
        totalPages: number;
    }> {
        try {
            const controller = new AbortController();
            const timeoutId = setTimeout(
                () => controller.abort(),
                API_CONFIG.TIMEOUT
            );

            const url = buildApiUrl('history', { page, size });
            const response = await fetch(url.toString(), {
                signal: controller.signal,
            });

            clearTimeout(timeoutId);

            if (!response.ok) {
                throw new Error(
                    `Failed to fetch history with status: ${response.status}`
                );
            }

            const historyData: PaginatedResponse<HistoryItem> =
                await response.json();

            return {
                items: historyData.content,
                totalPages: historyData.totalPages,
            };
        } catch (error) {
            if (error instanceof Error && error.name === 'AbortError') {
                throw new Error('Request timed out while fetching history.');
            }
            console.error('Error in fetchHistory:', error);
            throw error;
        }
    },
};
