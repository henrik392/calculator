<template>
    <ul class="history" v-if="history.length" v-for="item in history.slice().reverse()" :key="item.id">
        <li @click="emit('historyLog', item.expression)">{{ item.expression }} = {{ item.result }}</li>
    </ul>
</template>

<script setup lang="ts">
const props = defineProps<{
    history: { expression: string, result: string, id: number }[]
}>()

const emit = defineEmits(['historyLog'])
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