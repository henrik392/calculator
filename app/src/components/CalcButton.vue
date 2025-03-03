<template>
    <button
        class="h-16 p-3 text-2xl font-bold bg-gray-200 border-gray-300 rounded-lg shadow-md cursor-pointer hover:bg-gray-300 active:bg-gray-400 max-w-18 border-3"
        :class="specialClass"
        @click="onClick"
    >
        <slot></slot>
    </button>
</template>

<script setup lang="ts">
defineProps<{
    onClick: () => void;
    specialClass?: string;
}>();
</script>
