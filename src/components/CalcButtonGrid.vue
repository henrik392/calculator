<template>
    <div class="calc-button-grid">
        <button @click="clear">C</button>
        <button @click="ans">ANS</button>
        <button @click="del">DEL</button>
        <button @click="addChar('+')">+</button>
        <button @click="addChar('1')">1</button>
        <button @click="addChar('2')">2</button>
        <button @click="addChar('3')">3</button>
        <button @click="addChar('-')">-</button>

        <button @click="addChar('4')">4</button>
        <button @click="addChar('5')">5</button>
        <button @click="addChar('6')">6</button>
        <button @click="addChar('*')">*</button>

        <button @click="addChar('7')">7</button>
        <button @click="addChar('8')">8</button>
        <button @click="addChar('9')">9</button>
        <button @click="addChar('/')">/</button>

        <button @click="addChar('.')">.</button>
        <button @click="addChar('0')">0</button>
        <button class="equals-button" @click="calculate">=</button>
    </div>
</template>

<script setup lang="ts">
const props = defineProps<{
    clear: () => void;
    ans: () => void;
    del: () => void;
    addChar: (char: string) => void;
    calculate: () => void;
}>();
</script>

<style scoped>
.calc-button-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 5px;
}

.equals-button {
    grid-column: span 2;
}
</style>
