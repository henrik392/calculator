<!-- src/components/StatusMessage.vue -->
<script setup lang="ts">
defineProps<{
    type: 'success' | 'error' | 'loading';
    message?: string;
}>();
</script>

<template>
    <div class="status-message" :class="type">
        <span v-if="type === 'loading'">⏳</span>
        <span v-else-if="type === 'success'">✅</span>
        <span v-else>❌</span>
        {{
            message ||
            {
                loading: 'Submitting...',
                success: 'Submission successful!',
                error: 'Submission failed!',
            }[type]
        }}
    </div>
</template>

<style scoped>
.status-message {
    padding: 1rem;
    margin: 1rem 0;
    border-radius: 4px;
}

.success {
    background-color: #dff0d8;
    color: #3c763d;
}

.error {
    background-color: #f2dede;
    color: #a94442;
}

.loading {
    background-color: #d9edf7;
    color: #31708f;
}
</style>
