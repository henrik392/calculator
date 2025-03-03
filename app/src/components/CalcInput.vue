<!-- Litt unødvnendig å implementere custom v-model, men var litt gøy -->
<template>
    <input
        id="calculator-input"
        type="text"
        :value="localValue"
        @input="onInput"
        class="bg-[#BFBFA1] h-12 font-bold w-full text-right text-2xl px-4 border-black border-4 rounded-lg shadow-inner-top text-black"
        placeholder="Enter expression"
    />
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';

// Define props
const props = defineProps<{
    modelValue: string;
    'onUpdate:modelValue': (value: string) => void;
}>();

const localValue = ref(props.modelValue);

// Define a validation function
const valueIsValid = (value: string) => {
    const pattern = /^[0-9+\-*/.]*$/; // Only allow numbers and math symbols
    return value.match(pattern) !== null;
};

const onInput = (event: Event) => {
    const target = event.target as HTMLInputElement;
    const value = target.value;
    if (valueIsValid(value)) {
        localValue.value = value;
        props['onUpdate:modelValue'](value);
    } else {
        target.value = props.modelValue;
    }
};

// Sync localValue with modelValue when the parent updates modelValue
watch(
    () => props.modelValue,
    (newValue) => {
        localValue.value = newValue;
    }
);
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Doto:wght@900&display=swap');

#calculator-input {
    font-family: 'Doto';
}

.doto-calculator {
    font-family: 'Doto', serif;
    font-optical-sizing: auto;
    font-weight: 900;
    font-style: normal;
    font-variation-settings: 'ROND' 0;
}

.shadow-inner-top {
    box-shadow: inset 0 5px 5px rgba(81, 81, 81, 0.3);
}
</style>
