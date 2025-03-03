import { ref, computed, onMounted, onBeforeUnmount } from 'vue';
import { calculatorService } from '../services/calculatorService';

export function useCalculator() {
    // State
    const currentInput = ref('');
    const error = ref<string | null>(null);
    const isLoading = ref(false);
    const lastAnswer = ref('0');

    // Computed values
    const hasError = computed(() => error.value !== null);
    const hasInput = computed(() => currentInput.value.trim().length > 0);

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

            return result;
        } catch (err) {
            error.value =
                err instanceof Error
                    ? err.message
                    : 'An error occurred during calculation';
            console.error('Calculation error:', err);
            currentInput.value = 'Err';
        } finally {
            isLoading.value = false;
        }
    };

    /**
     * Set expression
     */
    const setExpression = (expression: string) => {
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
        window.addEventListener('keydown', handleKeydown);
    });

    onBeforeUnmount(() => {
        window.removeEventListener('keydown', handleKeydown);
    });

    // Return everything needed by the component
    return {
        // State
        currentInput,
        isLoading,
        error,
        lastAnswer,

        // Computed
        hasError,
        hasInput,

        // Methods
        clear,
        ans,
        del,
        addChar,
        calculate,
        setExpression,
    };
}
