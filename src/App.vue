<template>
    <div id="app">
        <nav>
            <router-link to="/">Home</router-link>
            <router-link to="/contact">Contact</router-link>
        </nav>
        <router-view />
    </div>
</template>

<script setup lang="ts">
console.log('App.vue setup script');
</script>

<style scoped>
nav {
    display: flex;
    gap: 1rem;
    align-items: center;
    justify-content: center;
}
</style>
