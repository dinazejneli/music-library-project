import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/auth/LoginView.vue'
import RegisterView from '@/views/auth/RegisterView.vue'

import SongsView from '@/views/songs/SongsView.vue'
import SongCreateView from '@/views/songs/SongCreateView.vue'
import SongEditView from '@/views/songs/SongEditView.vue'

import AlbumsView from '@/views/albums/AlbumsView.vue'
import AlbumCreateView from '@/views/albums/AlbumCreateView.vue'
import AlbumEditView from '@/views/albums/AlbumEditView.vue'

import { useAuthStore } from '@/stores/authStore'

const routes = [

    {
        path: '/',
        component: HomeView,
        meta: {
            requiresAuth: true
        }
    },

    {
        path: '/login',
        component: LoginView
    },
    {
        path: '/register',
        component: RegisterView
    },

    {
        path: '/songs',
        component: SongsView,
        meta: {
            requiresAuth: true
        }
    },

    {
        path: '/songs/create',
        component: SongCreateView,
        meta: {
            requiresAuth: true
        }
    },

    {
        path: '/songs/edit/:id',
        component: SongEditView,
        meta: {
            requiresAuth: true
        }
    },

    {
        path: '/albums',
        component: AlbumsView,
        meta: {
            requiresAuth: true,
            roles: ['ADMIN', 'MANAGER']
        }
    },
    

    {
        path: '/albums/create',
        component: AlbumCreateView,
        meta: {
            requiresAuth: true,
            roles: ['ADMIN', 'MANAGER']
        }
    },

    {
        path: '/albums/edit/:id',
        component: AlbumEditView,
        meta: {
            requiresAuth: true,
            roles: ['ADMIN', 'MANAGER']
        }
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

    if (
        to.path === '/login' &&
        authStore.isAuthenticated
    ) {

        next('/')
        return
    }

    if (
        to.meta.roles &&
        !to.meta.roles.includes(
            authStore.user?.role
        )
    ) {

        next('/')
        return
    }

    next()
})

export default router