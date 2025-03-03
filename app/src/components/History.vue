<template>
    <div class="history-container">
        <div class="bg-[#BFBFA1] border-black border-3 rounded-lg">
            <ul
                class=""
                v-if="history.length"
                v-for="item in history"
                :key="item.id"
            >
                <li
                    @click="emit('historyLog', item.expression)"
                    class="h-8 font-bold w-full text-right text-lg px-4 text-black flex items-center justify-end border-b-[1px] border-dashed overflow-clip whitespace-nowrap"
                >
                    {{ item.expression }} = {{ item.result }}
                </li>
            </ul>
            <div v-else>No history available</div>
        </div>

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
