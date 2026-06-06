<script setup>

import { reactive, ref } from 'vue'
import { useRouter, RouterLink } from 'vue-router'
import client from '@/helpers/client'

const router = useRouter()

const form = reactive({
    username: '',
    password: ''
})

const errorMessage = ref('')
const successMessage = ref('')

async function handleRegister() {

    errorMessage.value = ''
    successMessage.value = ''

    try {

        await client.post(
            '/auth/register',
            form
        )

        successMessage.value =
            'Account created successfully'

        setTimeout(() => {

            router.push('/login')

        }, 1500)

    } catch (error) {

        errorMessage.value =
            error.response?.data ||
            'Registration failed'
    }
}

</script>

<template>

    <div class="auth-page">

        <div class="auth-card">

            <h1>
                Create Account
            </h1>

            <p class="subtitle">
                Join Music Library
            </p>

            <form @submit.prevent="handleRegister">

                <input
                    v-model="form.username"
                    placeholder="Username"
                    required
                >

                <input
                    v-model="form.password"
                    type="password"
                    placeholder="Password"
                    required
                >

                <button
                    type="submit"
                    class="auth-btn"
                >
                    Register
                </button>

            </form>

            <p
                v-if="errorMessage"
                class="error"
            >
                {{ errorMessage }}
            </p>

            <p
                v-if="successMessage"
                class="success"
            >
                {{ successMessage }}
            </p>

            <div class="auth-footer">

                Already have an account?

                <RouterLink to="/login">

                    Login

                </RouterLink>

            </div>

        </div>

    </div>

</template>

<style scoped>

.auth-page {

    min-height: 100vh;

    display: flex;

    justify-content: center;

    align-items: center;

    background:
        linear-gradient(
            135deg,
            #0f172a,
            #1e3a8a
        );
}

.auth-card {

    width: 420px;

    padding: 40px;

    border-radius: 20px;

    background: white;

    box-shadow:
        0 20px 50px rgba(0,0,0,.25);
}

h1 {

    text-align: center;

    margin-bottom: 10px;

    color: #0f172a;
}

.subtitle {

    text-align: center;

    color: #64748b;

    margin-bottom: 30px;
}

input {

    width: 100%;

    padding: 14px;

    margin-bottom: 16px;

    border-radius: 10px;

    border: 1px solid #d1d5db;

    font-size: 15px;
}

input:focus {

    outline: none;

    border-color: #3b82f6;
}

.auth-btn {

    width: 100%;

    padding: 14px;

    border: none;

    border-radius: 10px;

    background: #2563eb;

    color: white;

    font-size: 15px;

    font-weight: 700;

    cursor: pointer;
}

.auth-btn:hover {

    background: #1d4ed8;
}

.error {

    margin-top: 15px;

    color: #dc2626;

    text-align: center;
}

.success {

    margin-top: 15px;

    color: #16a34a;

    text-align: center;
}

.auth-footer {

    margin-top: 25px;

    text-align: center;

    color: #64748b;
}

.auth-footer a {

    color: #2563eb;

    font-weight: 700;
}

</style>