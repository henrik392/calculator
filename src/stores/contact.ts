// State for contact page using Pinia, should store name and email

import { defineStore } from 'pinia';

export const useContactStore = defineStore('contact', {
    state: () => ({
        name: '',
        email: '',
        message: '',
    }),
    actions: {
        setName(name: string) {
            this.name = name;
        },
        setEmail(email: string) {
            this.email = email;
        },
        setMessage(message: string) {
            this.message = message;
        },
    },
});
