<template>
    <h1>Log in</h1>
    <form @submit.prevent="login">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="username" />
        <label for="password">Password</label>
        <input type="password" id="password" v-model="password" />
        <button
            :disabled="isDisabled"
            class="disabled:opacity-70"
            type="submit"
        >
            Log in
        </button>
        <p v-if="error" class="error-message">{{ error }}</p>
        <p v-if="success" class="success-message">{{ success }}</p>
    </form>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';

const username = ref('');
const password = ref('');
const error = ref('');
const success = ref('');
const isDisabled = ref(true);
const router = useRouter();

const login = async () => {
    if (username.value && password.value) {
        try {
            success.value = 'Logged in successfully!';
            error.value = '';
            router.push('/home'); // redirect after successful login
        } catch (err) {
            error.value = 'Login failed. Please try again.';
            success.value = '';
        }
    }
};

watch([username, password], () => {
    isDisabled.value = !username.value || !password.value;
});
</script>

<style scoped>
.error-message {
    color: red;
    font-size: 0.8em;
}
.success-message {
    color: green;
    margin-top: 1rem;
}
.disabled {
    opacity: 0.7;
    cursor: not-allowed;
}
</style>
