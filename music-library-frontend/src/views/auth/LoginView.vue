<script setup>

import { reactive, ref } from 'vue'
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

    <div>

        <h1>Login</h1>

        <form @submit.prevent="handleLogin">

            <div>
                <input
                    v-model="form.username"
                    placeholder="Username"
                >
            </div>

            <div>
                <input
                    v-model="form.password"
                    type="password"
                    placeholder="Password"
                >
            </div>

            <button type="submit">
                Login
            </button>

        </form>

        <p v-if="errorMessage">
            {{ errorMessage }}
        </p>

    </div>

</template>