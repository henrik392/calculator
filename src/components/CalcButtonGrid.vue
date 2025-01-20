<template>
    <div class="calc-button-grid">
        <button @click="">C</button>
        <button @click="">ANS</button>
        <button @click="">DEL</button>
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

        <button></button>
        <button @click="addChar('0')">0</button>
        <button @click="addChar('.')">.</button>
        <button @click="">=</button>
    </div>
</template>

<script setup lang="ts">
const props = defineProps<{
    addChar: (char: string) => void
}>()
</script>

<style scoped>
.calc-button-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 5px;
}
</style>