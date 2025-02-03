import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../HomePage.vue'; // Replace with your actual home component
import ContactPage from '../ContactPage.vue'; // Replace with your new contact form

const routes = [
  { path: '/', component: HomePage, name: 'home' },
  { path: '/contact', component: ContactPage, name: 'contact' },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;