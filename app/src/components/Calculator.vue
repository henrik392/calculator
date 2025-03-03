<template>
    <div
        class="w-full max-w-[400px] mx-12 bg-[#E3DFDC] rounded-2xl relative px-6 pt-8 pb-4 border-6 border-gray-200 mb-8"
    >
        <!--     Three retro flags with colors red yellow blue     -->
        <div class="absolute top-0 left-0 flex space-x-0.5 transform ml-8">
            <div class="w-3 h-8 bg-red-400"></div>
            <div class="w-3 h-8 bg-yellow-400"></div>
            <div class="w-3 h-8 bg-blue-400"></div>
        </div>
        <div class="mb-4">
            <CalcInput
                :modelValue="currentInput"
                @update:modelValue="setExpression"
            />
        </div>
        <CalcButtonGrid
            :clear="clear"
            :ans="ans"
            :del="del"
            :addChar="addChar"
            :calculate="handleCalculate"
            class="mb-6"
        />
        <History
            @historyLog="setExpression"
            :history="history"
            :totalPages="totalPages"
            :pageNumber="pageNumber"
            @update:pageNumber="setHistoryPage"
            class="mt-4"
        />
    </div>
</template>

<script setup lang="ts">
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
