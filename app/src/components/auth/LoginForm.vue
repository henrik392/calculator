src/components/auth/LoginForm.vue
<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';

const router = useRouter();
const authStore = useAuthStore();

const email = ref('');
const password = ref('');
const errorMessage = ref('');
const isLoading = ref(false);

const handleLogin = async () => {
    if (!email.value || !password.value) {
        errorMessage.value = 'Email and password are required';
        return;
    }

    isLoading.value = true;
    try {
        const success = await authStore.login(email.value, password.value);
        if (success) {
            router.push('/');
        } else {
            errorMessage.value = 'Invalid credentials';
        }
    } catch (error) {
        errorMessage.value = 'An error occurred during login';
    } finally {
        isLoading.value = false;
    }
};
</script>

<template>
    <div class="max-w-md w-full mx-auto p-6 bg-white rounded-lg shadow-md">
        <h2 class="text-2xl font-bold mb-6 text-center">Log In</h2>

        <form @submit.prevent="handleLogin" class="space-y-4">
            <div
                v-if="errorMessage"
                class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded"
            >
                {{ errorMessage }}
            </div>

            <div>
                <label
                    for="email"
                    class="block text-sm font-medium text-gray-700"
                    >Email</label
                >
                <input
                    id="email"
                    v-model="email"
                    type="email"
                    class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                    required
                />
            </div>

            <div>
                <label
                    for="password"
                    class="block text-sm font-medium text-gray-700"
                    >Password</label
                >
                <input
                    id="password"
                    v-model="password"
                    type="password"
                    class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                    required
                />
            </div>

            <div>
                <button
                    type="submit"
                    :disabled="isLoading"
                    class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
                >
                    <span v-if="isLoading">Logging in...</span>
                    <span v-else>Log In</span>
                </button>
            </div>

            <div class="text-center mt-4">
                <router-link
                    to="/signup"
                    class="text-sm text-indigo-600 hover:text-indigo-500"
                >
                    Don't have an account? Sign up
                </router-link>
            </div>
        </form>
    </div>
</template>
