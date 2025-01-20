<template>
    <CalcInput v-model="calcInput"/>
    <CalcButtonGrid :addChar="addChar" />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import CalcInput from './CalcInput.vue'
import CalcButtonGrid from './CalcButtonGrid.vue'

const calcInput = ref('')

const addChar = (char: string) => {
    calcInput.value += char
}
</script>

<style scoped>

</style>