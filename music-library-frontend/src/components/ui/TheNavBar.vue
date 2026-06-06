<script setup>

import { RouterLink } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const authStore = useAuthStore()

</script>

<template>

    <header class="navbar">

        <div class="logo">
            Music Library
        </div>

        <nav class="nav-links">

            <template v-if="authStore.isAuthenticated">

                <RouterLink to="/">
                    Home
                </RouterLink>

                <RouterLink to="/albums">
                    Albums
                </RouterLink>

                <RouterLink to="/songs">
                    Songs
                </RouterLink>

            </template>

            <template v-else>

                <RouterLink to="/login">
                    Login
                </RouterLink>

                <RouterLink to="/register">
                    Register
                </RouterLink>

            </template>

        </nav>

        <button
            v-if="authStore.isAuthenticated"
            class="logout-btn"
            @click="authStore.logout"
        >
            Logout
        </button>

    </header>

</template>



<style scoped>

.navbar {

    background:
            linear-gradient(
                    90deg,
                    #312e81,
                    #4338ca
            );

    color: white;

    height: 90px;

    display: flex;

    align-items: center;

    justify-content: space-between;

    padding: 0 50px;

    box-shadow:
            0 4px 12px rgba(0,0,0,0.15);
}

.logo {

    font-size: 2.3rem;

    font-weight: 900;

    letter-spacing: 2px;
}

.nav-links {

    display: flex;

    gap: 40px;

    margin-left: auto;

    margin-right: 40px;
}

.nav-links a {

    color: rgba(255,255,255,0.9);

    font-size: 1.15rem;

    font-weight: 700;

    text-decoration: none;

    transition: .2s;
}

.nav-links a:hover {

    color: #38bdf8;
}

.nav-links a.router-link-active {

    color: #67e8f9;
}

.logout-btn {

    background: #1a1681;

    color: white;

    padding: 12px 22px;

    border: none;

    border-radius: 8px;

    font-size: 1rem;

    font-weight: 700;

    cursor: pointer;

    transition: 0.2s;
}

.logout-btn:hover {

    background: #b91c1c;
}

</style>