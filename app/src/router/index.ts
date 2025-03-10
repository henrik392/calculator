import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../HomePage.vue';
import ContactPage from '../ContactPage.vue';
import LoginPage from '../LoginPage.vue';
import { useAuthStore } from '@/stores/auth';
import SignupPage from '@/SignupPage.vue';

const routes = [
    { path: '/', component: HomePage, name: 'home', requiresAuth: true },
    { path: '/login', component: LoginPage, name: 'login' },
    { path: '/signup', component: SignupPage, name: 'signup' },
    { path: '/contact', component: ContactPage, name: 'contact' },
];

const router = createRouter({
    history: createWebHistory(),
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
