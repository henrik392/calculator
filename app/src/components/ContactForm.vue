<template>
    <form :validation-schema="schema" @submit="onSubmit">
        <div class="form-group">
            <label for="name">Name:</label>
            <input
                v-model="name"
                v-bind="nameAttrs"
                name="name"
                type="text"
                class="form-control"
                data-test="name-input"
            />
            <span>{{ errors.name }}</span>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input
                v-model="email"
                v-bind="emailAttrs"
                name="email"
                type="email"
                class="form-control"
                data-test="email-input"
            />
            <span>{{ errors.email }}</span>
        </div>

        <div class="form-group">
            <label for="message">Message:</label>
            <textarea
                v-model="message"
                v-bind="messageAttrs"
                name="message"
                class="form-control"
                data-test="message-input"
            />
            <span>{{ errors.message }}</span>
        </div>

        <button
            :disabled="!meta.valid || isSubmitting"
            type="submit"
            data-test="submit-button"
        >
            {{ isSubmitting ? 'Submitting...' : 'Submit' }}
        </button>

        <StatusMessage
            v-if="submissionStatus !== 'idle'"
            :type="submissionStatus"
            :message="serverMessage"
            data-test="status-message"
        />
    </form>
</template>

<script setup lang="ts">
import { useForm } from 'vee-validate';
import * as yup from 'yup';
import { useContactStore } from '@/stores/contact';
import { watch, ref, computed } from 'vue';
import StatusMessage from '@/components/StatusMessage.vue';

interface Submission {
    name: string;
    email: string;
    message: string;
}

interface ApiResponse {
    status: 'success' | 'error';
    message?: string;
}

// Status state
const submissionStatus = ref<'idle' | 'loading' | 'success' | 'error'>('idle');
const serverMessage = ref<string>('');

const contactStore = useContactStore();

// Validation schema
const schema = yup.object({
    name: yup.string().required('Name is required'),
    email: yup
        .string()
        .required('Email is required')
        .email('Valid email required'),
    message: yup.string().required('Message is required'),
});

const { defineField, errors, handleSubmit, resetForm, meta, isSubmitting } =
    useForm({
        validationSchema: schema,
        initialValues: {
            name: contactStore.name,
            email: contactStore.email,
            message: contactStore.message,
        },
        validateOnMount: true,
    });

const [name, nameAttrs] = defineField('name');
const [email, emailAttrs] = defineField('email');
const [message, messageAttrs] = defineField('message');

watch(name, (newName) => {
    contactStore.setName(newName);
});

watch(email, (newEmail) => {
    contactStore.setEmail(newEmail);
});

watch(message, (newMessage) => {
    contactStore.setMessage(newMessage);
});

const postValues = async (values: Submission) => {
    const response = await fetch('http://localhost:3000/contact', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(values),
    });

    if (!response.ok) {
        throw new Error('Submission failed');
    }

    const data: ApiResponse = await response.json();
    return data; // return the response data for use
};

const onSubmit = handleSubmit(async (values: Submission) => {
    submissionStatus.value = 'loading';

    try {
        const data = await postValues(values);

        submissionStatus.value = 'success';
        serverMessage.value = data.message || 'Submission successful!';

        resetForm({
            values: {
                name: values.name,
                email: values.email,
                message: '',
            },
        });
    } catch (error) {
        submissionStatus.value = 'error';
        serverMessage.value = 'Submission failed!';
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
.disabled {
    opacity: 0.7;
    cursor: not-allowed;
}
</style>
