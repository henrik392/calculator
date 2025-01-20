<!-- Veldig overkomplisert, ville bare teste med custom v-model -->

<template>
    <input type="text" v-model="inputValue" />
</template>

<script setup lang="ts">
import { computed } from 'vue'
const props = defineProps<{
    modelValue: string
    'onUpdate:modelValue': (value: string) => void
}>()

const inputValue = computed({
    get: () => props.modelValue,
    set: (value: string) => props['onUpdate:modelValue'](value)
})
</script>

<style scoped>
input {
    width: 100px;
    height: 30px;
    background-color: white;
    color: black;
    font-weight: bold;
}
</style>