import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import client from '@/helpers/client'
import router from '@/router'

export const useAuthStore = defineStore('auth', () => {

    const token = ref(localStorage.getItem('token'))
    const user = ref(
        JSON.parse(localStorage.getItem('user')) || null
    )

    const isAuthenticated = computed(() => !!token.value)

    const isAdmin = computed(() =>
        user.value?.role === 'ADMIN'
    )

    const isManager = computed(() =>
        user.value?.role === 'MANAGER'
    )

    const isEmployee = computed(() =>
        user.value?.role === 'EMPLOYEE'
    )

    async function login(credentials) {

        const response = await client.post(
            '/auth/login',
            credentials
        )

        token.value = response.data.token

        localStorage.setItem(
            'token',
            response.data.token
        )

        /*
           Temporary user object.
           We will improve this later.
        */
        user.value = {
            username: response.data.username,
            role: response.data.role
        }

        localStorage.setItem(
            'user',
            JSON.stringify(user.value)
        )

        router.push('/')
    }

    function logout() {

        token.value = null
        user.value = null

        localStorage.removeItem('token')
        localStorage.removeItem('user')

        router.push('/login')
    }

    return {
        token,
        user,
        isAuthenticated,
        isAdmin,
        isManager,
        isEmployee,
        login,
        logout
    }
})