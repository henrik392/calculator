<template>
    <ul class="history" v-if="history.length" v-for="item in history" :key="item.id">
        <li>{{ item.expression }} = {{ item.result }}</li>
    </ul>
</template>

<script setup lang="ts">
const props = defineProps<{
    history: { expression: string, result: string, id: number }[]
}>()
</script>

<style scoped>
ul {
    list-style-type: none;
    padding: 0;
}
</style>