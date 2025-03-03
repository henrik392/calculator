import { ref, computed, onMounted, onBeforeUnmount } from 'vue';
import {
    calculatorService,
    type HistoryItem,
} from '../services/calculatorService';
import { API_CONFIG } from '../config/api';

export interface HistoryEntry {
    expression: string;
    result: string;
    id: number;
}

/**
 * Calculator composable that handles calculator logic and state
 * Separates UI concerns from calculation and API logic
 */
export function useCalculator() {
    // State
    const currentInput = ref('');
    const history = ref<HistoryEntry[]>([]);
    const pageNumber = ref(1);
    const totalPages = ref(1);
    const pageSize = API_CONFIG.DEFAULT_PAGE_SIZE;
    const error = ref<string | null>(null);
    const isLoading = ref(false);
    const lastAnswer = ref('0');

    // Computed values
    const hasError = computed(() => error.value !== null);
    const hasInput = computed(() => currentInput.value.trim().length > 0);
    const canMoveNext = computed(() => pageNumber.value < totalPages.value);
    const canMovePrevious = computed(() => pageNumber.value > 1);

    /**
     * Clear the input and error state
     */
    const clear = () => {
        currentInput.value = '';
        error.value = null;
    };

    /**
     * Insert the previous answer into the expression
     */
    const ans = () => {
        currentInput.value += 'ANS';
    };

    /**
     * Delete the last character or whole keyword from input
     */
    const del = () => {
        error.value = null;

        // If last digit is a letter, remove the whole word
        if (currentInput.value.slice(-1).match(/[a-zA-Z]/)) {
            currentInput.value = currentInput.value.replace(/[a-zA-Z]+$/, '');
            return;
        }

        currentInput.value = currentInput.value.slice(0, -1);
    };

    /**
     * Add a character to the input
     */
    const addChar = (char: string) => {
        error.value = null;
        currentInput.value += char;
    };

    /**
     * Get the previous answer to substitute for ANS keyword
     */
    const getPreviousAnswer = (): string => {
        return lastAnswer.value;
    };

    /**
     * Calculate the expression
     */
    const calculate = async () => {
        if (!hasInput.value) {
            return;
        }

        isLoading.value = true;
        error.value = null;

        try {
            const processedExpression = currentInput.value.replace(
                /ANS/g,
                getPreviousAnswer()
            );
            const result =
                await calculatorService.calculate(processedExpression);

            // Update state
            lastAnswer.value = result.toString();
            currentInput.value = result.toString();
            pageNumber.value = 1; // Reset to first page
            await fetchHistory();
        } catch (err) {
            error.value =
                err instanceof Error
                    ? err.message
                    : 'An error occurred during calculation';
            console.error('Calculation error:', err);
        } finally {
            isLoading.value = false;
        }
    };

    /**
     * Fetch calculation history
     */
    const fetchHistory = async () => {
        isLoading.value = true;
        error.value = null;

        try {
            // API uses zero-based paging
            const { items, totalPages: pages } =
                await calculatorService.fetchHistory(
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

            // Update last answer if history has items
            if (items.length > 0 && !hasInput.value) {
                lastAnswer.value = items[0].result;
            }
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

    /**
     * Set expression from history item
     */
    const setExpressionFromHistory = (expression: string) => {
        currentInput.value = expression;
        error.value = null;
    };

    /**
     * Event handler for keyboard events
     */
    const handleKeydown = (event: KeyboardEvent) => {
        // Handle Enter key for calculation
        if (event.key === 'Enter') {
            calculate();
            return;
        }

        // Handle Escape key to clear
        if (event.key === 'Escape') {
            clear();
            return;
        }

        // Handle Backspace key
        if (event.key === 'Backspace') {
            del();
            return;
        }
    };

    // Lifecycle hooks
    onMounted(() => {
        fetchHistory();
        window.addEventListener('keydown', handleKeydown);
    });

    onBeforeUnmount(() => {
        window.removeEventListener('keydown', handleKeydown);
    });

    // Return everything needed by the component
    return {
        // State
        currentInput,
        history,
        pageNumber,
        totalPages,
        isLoading,
        error,

        // Computed
        hasError,
        hasInput,
        canMoveNext,
        canMovePrevious,

        // Methods
        clear,
        ans,
        del,
        addChar,
        calculate,
        fetchHistory,
        setHistoryPage,
        setExpressionFromHistory,
    };
}
