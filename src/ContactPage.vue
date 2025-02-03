<template>
    <div class="contact-form">
        <form :validation-schema="schema" @submit="onSubmit">
            <div class="form-group">
                <label for="name">Name:</label>
                <field
                    v-model="name"
                    name="name"
                    type="text"
                    class="form-control"
                />
                <ErrorMessage name="name" class="error-message" />
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <Field name="email" type="email" class="form-control" />
                <ErrorMessage name="email" class="error-message" />
            </div>

            <div class="form-group">
                <label for="message">Message:</label>
                <Field name="message" as="textarea" class="form-control" />
                <ErrorMessage name="message" class="error-message" />
            </div>

            <button
                type="submit"
                :disabled="isSubmitting || submissionStatus === 'pending'"
                class="submit-btn"
            >
                {{
                    submissionStatus === 'pending' ? 'Submitting...' : 'Submit'
                }}
            </button>

            <div v-if="submissionStatus === 'success'" class="success-message">
                Thank you for your submission!
            </div>
            <div v-if="submissionStatus === 'error'" class="error-message">
                Error submitting form. Please try again.
            </div>
        </form>
    </div>
</template>

<script setup lang="ts">
import { useForm } from 'vee-validate';
import * as yup from 'yup';
import { useContactStore } from './stores/contact';
import { ref } from 'vue';

const contactStore = useContactStore();
const submissionStatus = ref<'idle' | 'pending' | 'success' | 'error'>('idle');

// Validation schema
const schema = yup.object({
    name: yup.string().required('Name is required'),
    email: yup
        .string()
        .required('Email is required')
        .email('Valid email required'),
    message: yup.string().required('Message is required'),
});

const { defineField, errors, handleSubmit } = useForm({
    validationSchema: schema,
});

const [email, emailAttrs] = defineField('email');
const [password, passwordAttrs] = defineField('password');

const onSubmit = handleSubmit((values) => {
    alert(JSON.stringify(values, null, 2));
});
</script>

<style scoped>
.error-message {
    color: red;
    font-size: 0.8em;
}
.success-message {
    color: green;
    margin-top: 1rem;
}
</style>
