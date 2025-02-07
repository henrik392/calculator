import { mount } from '@vue/test-utils';
import ContactForm from '@/components/ContactForm.vue';
import { describe, expect, it, vi, beforeEach } from 'vitest';
import { createPinia, setActivePinia } from 'pinia';

// Add these lines
// import { useContactStore } from '@/stores/contact';
// import type { App } from 'vue';

let pinia: ReturnType<typeof createPinia>;
// let app: App;

describe('ContactForm', async () => {
    beforeEach(() => {
        pinia = createPinia();
        setActivePinia(pinia);
    });

    it('renders form', () => {
        const wrapper = mount(ContactForm);

        expect(wrapper.find('form').exists()).toBe(true);
    });

    it('postValues return same message as sent', async () => {
        const wrapper = mount(ContactForm);

        const { postValues } = wrapper.vm as any;

        const message = 'Test message';
        const response = await postValues({
            name: 'Test User',
            email: 'test@example.com',
            message,
        });

        expect(response.message).toBe(message);
    });
});
