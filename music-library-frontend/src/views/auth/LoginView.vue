```vue
<script setup>

import { reactive, ref } from 'vue'
import { RouterLink } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const authStore = useAuthStore()

const form = reactive({
    username: '',
    password: ''
})

const errorMessage = ref('')

async function handleLogin() {

    errorMessage.value = ''

    try {

        await authStore.login(form)

    } catch (error) {

        errorMessage.value = error
    }
}

</script>

<template>

    <div class="auth-page">

        <div class="auth-card">

            <h1>Welcome Back</h1>

            <p class="subtitle">
                Login to Music Library
            </p>

            <form @submit.prevent="handleLogin">

                <input
                    v-model="form.username"
                    placeholder="Username"
                >

                <input
                    v-model="form.password"
                    type="password"
                    placeholder="Password"
                >

                <button
                    type="submit"
                    class="auth-btn"
                >
                    Login
                </button>

            </form>

            <p
                v-if="errorMessage"
                class="error"
            >
                {{ errorMessage }}
            </p>

            <div class="auth-footer">

                Don't have an account?

                <RouterLink to="/register">
                    Register
                </RouterLink>

            </div>

        </div>

    </div>

</template>

<style scoped>

.auth-page {

    min-height: calc(100vh - 90px);

    display: flex;

    justify-content: center;

    align-items: center;

    padding: 40px;
}

.auth-card {

    width: 100%;

    max-width: 450px;

    background: #111827;

    padding: 40px;

    border-radius: 20px;

    box-shadow:
            0 0 35px rgba(79,70,229,.35);
}

.auth-card h1 {

    color: white;

    text-align: center;

    margin-bottom: 10px;
}

.subtitle {

    text-align: center;

    color: #9ca3af;

    margin-bottom: 30px;
}

input {

    width: 100%;

    margin-bottom: 16px;

    padding: 14px;

    border: none;

    border-radius: 12px;

    background: #1f2937;

    color: white;
}

input::placeholder {

    color: #9ca3af;
}

.auth-btn {

    width: 100%;

    background: #4f46e5;

    color: white;

    padding: 14px;

    border: none;

    border-radius: 12px;

    font-weight: 700;

    cursor: pointer;

    transition: .2s;
}

.auth-btn:hover {

    background: #6366f1;
}

.error {

    color: #ef4444;

    text-align: center;

    margin-top: 15px;
}

.auth-footer {

    margin-top: 20px;

    text-align: center;

    color: #9ca3af;
}

.auth-footer a {

    color: #60a5fa;

    margin-left: 5px;
}

</style>
```
