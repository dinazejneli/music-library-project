import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import client from '@/helpers/client'
import router from '@/router'

export const useAuthStore = defineStore('auth', () => {

    const token = ref(localStorage.getItem('token'))
    const user = ref(null)

    const isAuthenticated = computed(() => !!token.value)

    async function login(credentials) {

        try {

            const response = await client.post(
                '/auth/login',
                credentials
            )

            token.value = response.data.token

            localStorage.setItem(
                'token',
                response.data.token
            )

            user.value = {
                username: response.data.username,
                role: response.data.role
            }

            router.push('/')

        } catch (error) {

            throw error.response?.data || error.message
        }
    }

    function logout() {

        token.value = null
        user.value = null

        localStorage.removeItem('token')

        router.push('/login')
    }

    return {
        token,
        user,
        isAuthenticated,
        login,
        logout
    }
})