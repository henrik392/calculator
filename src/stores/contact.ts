// State for contact page using Pinia, should store name and email

import { defineStore } from "pinia";

export const useContactStore = defineStore('contact', {
  state: () => ({
    name: '',
    email: ''
  }),
  actions: {
    setContactInfo(name: string, email: string) {
      this.name = name
      this.email = email
    }
  }
});