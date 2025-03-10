<template>
    <div id="app">
        <nav v-if="authStore.isAuthenticated" class="my-4">
            <button class="cursor-pointer" v-on:click="logout()">Logout</button>
            <router-link to="/">Home</router-link>
            <router-link to="/contact">Contact</router-link>
        </nav>
        <router-view />
    </div>
</template>

<script setup lang="ts">
import router from './router';
import { useAuthStore } from './stores/auth';

const authStore = useAuthStore();

const logout = () => {
    authStore.logout();
    router.push('/login');
};
</script>

<style scoped>
nav {
    display: flex;
    gap: 1rem;
    align-items: center;
    justify-content: center;
}
</style>
