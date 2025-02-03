<template>
    <CalcInput v-model="currentInput" />
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
    />
</template>

<script setup lang="ts">
import { ref } from 'vue';
import CalcInput from './CalcInput.vue';
import CalcButtonGrid from './CalcButtonGrid.vue';
import History from './History.vue';

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
    return history.value[history.value.length - 1]?.result || '0';
};

const addToHistory = (expression: string, result: string) => {
    history.value.push({ expression, result, id: history.value.length });
};

const calculate = () => {
    try {
        const result = eval(
            currentInput.value.replace(/ANS/g, getPreviousAnswer())
        );
        addToHistory(currentInput.value, result);
    } catch (error) {
        currentInput.value = 'Err';
    }
};

const addChar = (char: string) => {
    currentInput.value += char;
};
</script>

<style scoped></style>
