import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../HomePage.vue';
import ContactPage from '../ContactPage.vue';
import LoginPage from '../LoginPage.vue';

const routes = [
    { path: '/', component: HomePage, name: 'home', requiresAuth: true },
    { path: '/login', component: LoginPage, name: 'login' },
    { path: '/contact', component: ContactPage, name: 'contact' },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;

