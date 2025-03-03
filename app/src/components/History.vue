<template>
    <div class="history-container">
        <ul
            class="history"
            v-if="history.length"
            v-for="item in history.slice()"
            :key="item.id"
        >
            <li @click="emit('historyLog', item.expression)">
                {{ item.expression }} = {{ item.result }}
            </li>
        </ul>
        <div class="page-controls">
            <button
                @click="$emit('update:pageNumber', props.pageNumber - 1)"
                :disabled="isFirstPage"
            >
                Previous
            </button>
            <button
                @click="$emit('update:pageNumber', props.pageNumber + 1)"
                :disabled="!hasNextPage"
            >
                Next
            </button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { computed, defineProps, defineEmits } from 'vue';

const props = defineProps<{
    history: { expression: string; result: string; id: number }[];
    pageNumber: number;
    totalPages: number;
}>();

const emit = defineEmits(['historyLog', 'update:pageNumber']);

const updatePageNumber = (newPage: number) => {
    emit('update:pageNumber', newPage);
};

const hasNextPage = computed(() => props.pageNumber < props.totalPages);

const isFirstPage = computed(() => props.pageNumber === 1);
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
