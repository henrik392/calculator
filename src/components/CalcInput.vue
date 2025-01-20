<template>
    <input type="text" :value="localValue" @input="onInput"/>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue';

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
watch(() => props.modelValue, (newValue) => {
    localValue.value = newValue;
});
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
