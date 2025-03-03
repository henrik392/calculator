import { API_CONFIG, buildApiUrl } from '../config/api';

interface CalculationRequest {
    expression: string;
}

interface CalculationResponse {
    result: number;
}

/**
 * Service for calculator operations and history management
 */
export const calculatorService = {
    /**
     * Send calculation request to the API
     * @param expression Mathematical expression to evaluate
     * @returns Calculated result
     */
    async calculate(expression: string): Promise<number> {
        try {
            const controller = new AbortController();
            const timeoutId = setTimeout(
                () => controller.abort(),
                API_CONFIG.TIMEOUT
            );

            const request: CalculationRequest = { expression };
            const response = await fetch(buildApiUrl('calculate').toString(), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(request),
                signal: controller.signal,
            });

            clearTimeout(timeoutId);

            if (!response.ok) {
                throw new Error(
                    `Calculation failed with status: ${response.status}`
                );
            }

            const data: CalculationResponse = await response.json();
            return data.result;
        } catch (error) {
            if (error instanceof Error && error.name === 'AbortError') {
                throw new Error('Request timed out. Please try again.');
            }
            console.error('Error in calculate:', error);
            throw error;
        }
    },
};
