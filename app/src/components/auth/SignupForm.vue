<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';

const router = useRouter();
const authStore = useAuthStore();

const username = ref('');
const email = ref('');
const password = ref('');
const errorMessage = ref('');
const isLoading = ref(false);

const handleSignup = async () => {
    if (!username.value || !email.value || !password.value) {
        errorMessage.value = 'Username, email, and password are required';
        return;
    }

    isLoading.value = true;
    try {
        const success = await authStore.signup(
            username.value,
            email.value,
            password.value
        );
        if (success) {
            router.push('/');
        } else {
            errorMessage.value = 'User already exists';
        }
    } catch (error) {
        errorMessage.value = 'An error occurred during signup';
    } finally {
        isLoading.value = false;
    }
};
</script>

<template>
    <div class="max-w-md w-full mx-auto p-6 bg-white rounded-lg shadow-md">
        <h2 class="text-2xl font-bold mb-6 text-center">Sign Up</h2>

        <form @submit.prevent="handleSignup" class="space-y-4">
            <div
                v-if="errorMessage"
                class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded"
            >
                {{ errorMessage }}
            </div>

            <div>
                <label
                    for="username"
                    class="block text-sm font-medium text-gray-700"
                    >Username</label
                >
                <input
                    id="username"
                    v-model="username"
                    type="username"
                    class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                    required
                />
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
                    class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
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
                    class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                    required
                />
            </div>

            <div>
                <button
                    type="submit"
                    :disabled="isLoading"
                    class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-400 hover:bg-blue-500 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 cursor-pointer"
                >
                    <span v-if="isLoading">Signing up...</span>
                    <span v-else>Sign Up</span>
                </button>
            </div>

            <div class="text-center mt-4">
                <router-link
                    to="/login"
                    class="text-sm text-blue-400 hover:text-blue-500"
                >
                    Have an account? Sign in
                </router-link>
            </div>
        </form>
    </div>
</template>
