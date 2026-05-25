import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/auth/LoginView.vue'

import { useAuthStore } from '@/stores/authStore'

const routes = [

    {
        path: '/',
        component: HomeView,
        meta: { requiresAuth: true }
    },

    {
        path: '/login',
        component: LoginView
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {

    const authStore = useAuthStore()

    if (
        to.meta.requiresAuth &&
        !authStore.isAuthenticated
    ) {

        next('/login')
        return
    }

    next()
})

export default router