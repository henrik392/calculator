<template>
    <CalcInput
        :modelValue="currentInput"
        @update:modelValue="(value) => (currentInput = value)"
    />
    <CalcButtonGrid
        :clear="clear"
        :ans="ans"
        :del="del"
        :addChar="addChar"
        :calculate="calculate"
    />
    <History
        @historyLog="(expression) => (currentInput = expression)"
        :history="history"
        :totalPages="totalPages"
        :pageNumber="pageNumber"
        @update:pageNumber="
            (newPage) => {
                pageNumber = newPage;
                fetchHistory();
            }
        "
    />
</template>

<script setup lang="ts">
import { ref } from 'vue';
import CalcInput from './CalcInput.vue';
import CalcButtonGrid from './CalcButtonGrid.vue';
import History from './History.vue';

const pageNumber = ref(1);
const totalPages = ref(1);
const pageSize = 5;

const history = ref<{ expression: string; result: string; id: number }[]>([]);

const currentInput = ref('');

const clear = () => {
    currentInput.value = '';
};

const ans = () => {
    currentInput.value += 'ANS';
};

const del = () => {
    // If last digit is a letter, remove the whole word
    if (currentInput.value.slice(-1).match(/[a-zA-Z]+/)) {
        currentInput.value = currentInput.value.replace(/[a-zA-Z]+$/, '');
        return;
    }

    currentInput.value = currentInput.value.slice(0, -1);
};

const getPreviousAnswer = () => {
    return history.value[0]?.result || '0';
};

const calculate = async () => {
    try {
        const result = await evaluateExpression(
            currentInput.value.replace(/ANS/g, getPreviousAnswer())
        );
        currentInput.value = result.toString();
        pageNumber.value = 1;
        await fetchHistory();
    } catch (error) {
        console.error(error);
        currentInput.value = 'Err';
    }
};

interface PaginatedResponse<T> {
    content: T[];
    // Zero-indexed
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

interface HistoryRequest {
    page: number;
    size: number;
}

const fetchHistory = async () => {
    try {
        const url = new URL('http://localhost:8080/api/history');
        url.searchParams.append('page', (pageNumber.value - 1).toString());
        url.searchParams.append('size', pageSize.toString());

        const response = await fetch(url.toString());

        if (!response.ok) {
            throw new Error('Failed to fetch history');
        }

        const historyData: PaginatedResponse<HistoryItem> =
            await response.json();

        let id = 1;
        history.value = historyData.content.map((item) => ({
            expression: item.expression,
            result: item.result,
            id: id++,
        }));

        totalPages.value = historyData.totalPages;
        console.log('History fetched successfully:', history.value);
    } catch (error) {
        console.error(error);
    }
};

interface CalculationRequest {
    expression: string;
}

interface CalculationResponse {
    result: number;
}

const evaluateExpression = async (expression: string): Promise<number> => {
    const request = JSON.stringify({ expression: expression });
    const response = await fetch('http://localhost:8080/api/calculate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: request,
    });

    if (!response.ok) {
        throw new Error('Calculation failed');
    }

    const data: CalculationResponse = await response.json();
    return data.result;
};

const addChar = (char: string) => {
    currentInput.value += char;
};

fetchHistory();

// On enter, calculate the expression
window.addEventListener('keydown', (event) => {
    if (event.key === 'Enter') {
        calculate();
    }
});
</script>

<style scoped></style>
