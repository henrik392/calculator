<template>
    <CalcInput :modelValue="currentInput" @update:modelValue="setExpression" />
    <CalcButtonGrid
        :clear="clear"
        :ans="ans"
        :del="del"
        :addChar="addChar"
        :calculate="handleCalculate"
    />
    <History
        @historyLog="setExpression"
        :history="history"
        :totalPages="totalPages"
        :pageNumber="pageNumber"
        @update:pageNumber="setHistoryPage"
    />
</template>

<script setup lang="ts">
import { watch } from 'vue';
import { useCalculator } from '../composables/useCalculator';
import { useHistory } from '../composables/useHistory';
import CalcInput from './CalcInput.vue';
import CalcButtonGrid from './CalcButtonGrid.vue';
import History from './History.vue';

const {
    currentInput,
    isLoading: calcLoading,
    error: calcError,
    clear,
    ans,
    del,
    addChar,
    calculate,
    setExpression,
} = useCalculator();

const {
    history,
    pageNumber,
    totalPages,
    isLoading: historyLoading,
    error: historyError,
    fetchHistory,
    setHistoryPage,
} = useHistory();

// When calculation is successful, refresh history
const handleCalculate = async () => {
    const result = await calculate();
    if (result !== undefined) {
        // Refresh history to show the new calculation
        fetchHistory();
    }
};
</script>
