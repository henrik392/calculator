<template>
    <div class="contact-form">
        <form :validation-schema="schema" @submit="onSubmit">
            <div class="form-group">
                <label for="name">Name:</label>
                <input
                    v-model="name"
                    v-bind="nameAttrs"
                    name="name"
                    type="text"
                    class="form-control"
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
                />
                <span>{{ errors.message }}</span>
            </div>

            <button :disabled="!meta.valid || isSubmitting">Submit</button>
        </form>
    </div>
</template>

<script setup lang="ts">
import { useForm } from 'vee-validate';
import * as yup from 'yup';
import { useContactStore } from './stores/contact';
import { watch } from 'vue';

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

const onSubmit = handleSubmit((values) => {
    resetForm({
        values: {
            name: values.name,
            email: values.email,
            message: '',
        },
    });

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
.deactivated {
    color: grey;
}
</style>
