<template>
    <button
        class="bg-gray-200 rounded-lg p-3 shadow-md hover:bg-gray-300 active:bg-gray-400 font-bold max-w-18 h-16 text-2xl border-3 border-gray-300"
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
