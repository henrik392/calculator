import { mount } from '@vue/test-utils';
import ContactForm from '@/components/ContactForm.vue';
import { describe, expect, it, vi, beforeEach } from 'vitest';
import { createPinia, setActivePinia } from 'pinia';

// Add these lines
// import { useContactStore } from '@/stores/contact';
// import type { App } from 'vue';

let pinia: ReturnType<typeof createPinia>;
// let app: App;

describe('ContactForm', () => {
    beforeEach(() => {
        pinia = createPinia();
        setActivePinia(pinia);
    });

    // it('Show error for invalid email', async () => {
    //     const wrapper = mount(ContactForm);

    //     // Find inputs
    //     const emailInput = wrapper.find('#email-input');

    //     // Type one character and delete it to trigger validation
    //     await emailInput.setValue('a');

    //     // Wait for validation
    //     // HELP NEEDED HERE!

    //     expect(wrapper.text()).toContain('Valid email required');
    // });

    // it('disables button when invalid', async () => {
    //     const wrapper = mount(ContactForm, {
    //         global: {
    //             plugins: [pinia],
    //         },
    //     });

    //     // Wait for initial validation
    //     await wrapper.vm.$nextTick();

    //     // Check disabled state
    //     const button = wrapper.find('#submit-button');
    //     expect(button.attributes('disabled')).toBe('');
    // });

    // it('submits valid form', async () => {
    //     const wrapper = mount(ContactForm);
    //     (fetch as any).mockResolvedValue({
    //         ok: true,
    //         json: () => ({ status: 'success' }),
    //     });

    //     // Fill valid form
    //     await wrapper.find('input[name="name"]').setValue('Test');
    //     await wrapper.find('input[name="email"]').setValue('test@test.com');
    //     await wrapper.find('textarea[name="message"]').setValue('Hello');
    //     await wrapper.find('form').trigger('submit');

    //     // Check API call and message
    //     expect(fetch).toHaveBeenCalled();
    //     expect(wrapper.vm.submissionStatus).toBe('success');
    // });
});
