<template>
    <div class="contact-form">
        <form @submit="onSubmit">
            <div class="form-group">
                <label for="name">Name:</label>
                <Field name="name" type="text" class="form-control" />
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
import { object, string } from 'yup';
import { useContactStore } from './stores/contact';
import { ref } from 'vue';

const contactStore = useContactStore();
const submissionStatus = ref<'idle' | 'pending' | 'success' | 'error'>('idle');

// Validation schema
const schema = object({
    name: string().required('Name is required'),
    email: string().required('Email is required').email('Valid email required'),
    message: string().required('Message is required'),
});

const { handleSubmit, isSubmitting, resetForm } = useForm({
    validationSchema: schema,
    initialValues: {
        name: contactStore.name,
        email: contactStore.email,
        message: '',
    },
});

const onSubmit = handleSubmit(async (values) => {
    submissionStatus.value = 'pending';
    try {
        // Mock API call - replace with your actual API endpoint
        const response = await fetch('http://localhost:3000/submissions', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(values),
        });

        if (response.ok) {
            contactStore.setName(values.name);
            contactStore.setEmail(values.email);
            submissionStatus.value = 'success';
            resetForm({
                values: {
                    name: values.name,
                    email: values.email,
                    message: '',
                },
            });
        } else {
            submissionStatus.value = 'error';
        }
    } catch (error) {
        submissionStatus.value = 'error';
    }
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
