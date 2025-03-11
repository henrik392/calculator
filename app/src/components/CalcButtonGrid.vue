<template>
    <div class="grid grid-cols-4 gap-5 text-black">
        <CalcButton :onClick="clear">C</CalcButton>
        <CalcButton :onClick="ans">ANS</CalcButton>
        <CalcButton :onClick="del">DEL</CalcButton>
        <CalcButton :onClick="() => addChar('+')">+</CalcButton>
        <CalcButton :onClick="() => addChar('1')">1</CalcButton>
        <CalcButton :onClick="() => addChar('2')">2</CalcButton>
        <CalcButton :onClick="() => addChar('3')">3</CalcButton>
        <CalcButton :onClick="() => addChar('-')">-</CalcButton>

        <CalcButton :onClick="() => addChar('4')">4</CalcButton>
        <CalcButton :onClick="() => addChar('5')">5</CalcButton>
        <CalcButton :onClick="() => addChar('6')">6</CalcButton>
        <CalcButton :onClick="() => addChar('*')">*</CalcButton>

        <CalcButton :onClick="() => addChar('7')">7</CalcButton>
        <CalcButton :onClick="() => addChar('8')">8</CalcButton>
        <CalcButton :onClick="() => addChar('9')">9</CalcButton>
        <CalcButton :onClick="() => addChar('/')">/</CalcButton>

        <CalcButton :onClick="() => addChar('.')">.</CalcButton>
        <CalcButton :onClick="() => addChar('0')">0</CalcButton>
        <CalcButton class="bg-blue-500 col-span-2" :onClick="calculate"
            >=</CalcButton
        >
    </div>
</template>

<script setup lang="ts">
import CalcButton from './CalcButton.vue';

const props = defineProps<{
    clear: () => void;
    ans: () => void;
    del: () => void;
    addChar: (char: string) => void;
    calculate: () => void;
}>();
</script>

<style scoped>
#equals-button {
    grid-column: span 2;
}
</style>
