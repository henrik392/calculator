import { mount } from '@vue/test-utils';
import Calculator from '@/components/Calculator.vue';
import { describe, expect, it } from 'vitest';

describe('Calculator', () => {
    it('Display correct answer with buttons', async () => {
        const wrapper = mount(Calculator);

        // Find buttons by their text value
        const buttons = wrapper.findAll('button');
        const fiveButton = buttons.find((b) => b.text() === '5');
        const plusButton = buttons.find((b) => b.text() === '+');
        const threeButton = buttons.find((b) => b.text() === '3');
        const equalsButton = wrapper.find('#equals-button');

        // Simulate clicks
        await fiveButton?.trigger('click');
        await plusButton?.trigger('click');
        await threeButton?.trigger('click');
        await equalsButton.trigger('click');

        const input = wrapper.find('#calculator-input')
            .element as HTMLInputElement;
        expect(input.value).toBe('8');
    });

    it('Display correct answer with keyboard', async () => {
        const wrapper = mount(Calculator);
        const input = wrapper.find('#calculator-input');

        // Set value and trigger keydown
        await input.setValue('29*38');
        const equalsButton = wrapper.find('#equals-button');

        await equalsButton.trigger('click');

        const updatedInput = wrapper.find('#calculator-input')
            .element as HTMLInputElement;
        expect(updatedInput.value).toBe('1102');
    });
});
