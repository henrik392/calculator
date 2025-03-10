import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../HomePage.vue';
import ContactPage from '../ContactPage.vue';
import LoginPage from '../LoginPage.vue';
import { useAuthStore } from '@/stores/auth';
import { esbuildVersion } from 'vite';

const routes = [
    {
        path: '/',
        component: HomePage,
        name: 'home',
        // meta: { requiresAuth: true },
    },
    // { path: '/login', component: LoginPage, name: 'login' },
    // { path: '/signup', component: LoginPage, name: 'login' },
    { path: '/contact', component: ContactPage, name: 'contact' },
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});

router.beforeEach((to, _from, next) => {
    const authStore = useAuthStore();

    if (to.meta.requiresAuth && !authStore.isAuthenticated) {
        next('/login');
    } else {
        next();
    }
});

export default router;
