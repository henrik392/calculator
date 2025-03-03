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
import { useCalculator } from '../composables/useCalculator';

const {
    currentInput,
    history,
    pageNumber,
    totalPages,
    isLoading,
    error,
    hasError,
    hasInput,
    canMoveNext,
    canMovePrevious,
    clear,
    ans,
    del,
    addChar,
    calculate,
    fetchHistory,
    setHistoryPage,
    setExpressionFromHistory,
} = useCalculator();

import CalcInput from './CalcInput.vue';
import CalcButtonGrid from './CalcButtonGrid.vue';
import History from './History.vue';

fetchHistory();
</script>

<style scoped></style>
