import { ref, computed, onMounted } from 'vue';
import { historyService } from '../services/historyService';
import { API_CONFIG } from '../config/api';

export interface HistoryEntry {
    expression: string;
    result: string;
    id: number;
}

export function useHistory() {
    // State
    const history = ref<HistoryEntry[]>([]);
    const pageNumber = ref(1);
    const totalPages = ref(1);
    const pageSize = API_CONFIG.DEFAULT_PAGE_SIZE;
    const isLoading = ref(false);
    const error = ref<string | null>(null);

    // Computed values
    const hasError = computed(() => error.value !== null);
    const canMoveNext = computed(() => pageNumber.value < totalPages.value);
    const canMovePrevious = computed(() => pageNumber.value > 1);

    /**
     * Fetch calculation history
     */
    const fetchHistory = async () => {
        isLoading.value = true;
        error.value = null;

        try {
            // API uses zero-based paging
            const { items, totalPages: pages } =
                await historyService.fetchHistory(
                    pageNumber.value - 1,
                    pageSize
                );

            // Map API response to component model
            history.value = items.map((item, index) => ({
                expression: item.expression,
                result: item.result,
                id: index + 1,
            }));

            // Update state
            totalPages.value = pages;
        } catch (err) {
            error.value =
                err instanceof Error ? err.message : 'Failed to load history';
            console.error('History fetch error:', err);
        } finally {
            isLoading.value = false;
        }
    };

    /**
     * Set history page number and fetch history
     */
    const setHistoryPage = async (page: number) => {
        if (page < 1 || page > totalPages.value) return;

        pageNumber.value = page;
        await fetchHistory();
    };

    // Initialize history on mount
    onMounted(() => {
        fetchHistory();
    });

    return {
        // State
        history,
        pageNumber,
        totalPages,
        isLoading,
        error,

        // Computed
        hasError,
        canMoveNext,
        canMovePrevious,

        // Methods
        fetchHistory,
        setHistoryPage,
    };
}
