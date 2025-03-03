<template>
    <div class="history-container">
        <ul
            class="history"
            v-if="history.length"
            v-for="item in history"
            :key="item.id"
        >
            <li @click="emit('historyLog', item.expression)">
                {{ item.expression }} = {{ item.result }}
            </li>
        </ul>
        <div v-else>No history available</div>

        <div class="page-controls" v-if="totalPages > 1">
            <button
                @click="$emit('update:pageNumber', pageNumber - 1)"
                :disabled="pageNumber === 1"
            >
                Previous
            </button>
            <span>Page {{ pageNumber }} of {{ totalPages }}</span>
            <button
                @click="$emit('update:pageNumber', pageNumber + 1)"
                :disabled="pageNumber >= totalPages"
            >
                Next
            </button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';

const props = defineProps<{
    history: { expression: string; result: string; id: number }[];
    pageNumber: number;
    totalPages: number;
}>();

const emit = defineEmits(['historyLog', 'update:pageNumber']);
</script>

<style scoped>
ul {
    list-style-type: none;
    padding: 0;
}

li {
    padding: 10px;
    border: 1px solid #ccc;
    cursor: pointer;
}
</style>
