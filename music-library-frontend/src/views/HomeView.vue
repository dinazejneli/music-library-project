<script setup>

import { ref, onMounted } from 'vue'
import client from '@/helpers/client'
import { useAuthStore } from '@/stores/authStore'

const authStore = useAuthStore()

const songs = ref([])
const albums = ref([])

const quotes = [

    'Where words fail, music speaks.',
    'Music is what feelings sound like.',
    'One good thing about music, when it hits you, you feel no pain.',
    'Without music, life would be a mistake.',
    'Music can change the world.'
]

const randomQuote =
    quotes[Math.floor(Math.random() * quotes.length)]

async function loadData() {

    try {

        const songsResponse =
            await client.get('/songs')

        songs.value = songsResponse.data

    } catch (error) {

        console.error(error)
    }

    try {

        const albumsResponse =
            await client.get('/albums')

        albums.value = albumsResponse.data

    } catch (error) {

        console.error(error)
    }
}

onMounted(loadData)

</script>

<template>

<div class="home-page">

    <!-- HERO -->

    <div class="hero-card">

        <h1>
            Welcome back,
            {{ authStore.user?.username }} 
        </h1>

        <span class="role-badge">
            {{ authStore.user?.role }}
        </span>

    </div>

    <!-- STATS -->

    <div class="stats-grid">

        <div class="stat-card">

            <div class="stat-icon">
                🎵
            </div>

            <h2>
                {{ songs.length }}
            </h2>

            <p>
                Total Songs
            </p>

        </div>

        <div class="stat-card">

            <div class="stat-icon">
                💿
            </div>

            <h2>
                {{ albums.length }}
            </h2>

            <p>
                Total Albums
            </p>

        </div>

        <div class="stat-card">

            <div class="stat-icon">
                👤
            </div>

            <h2>
                {{ authStore.user?.role }}
            </h2>

            <p>
                Account Role
            </p>

        </div>

    </div>

    <!-- TRENDING ALBUMS -->

    <section>

        <h2 class="section-title">
            🔥 Trending Today
        </h2>

        <div class="albums-row">

            <div
                v-for="album in albums.slice(0, 4)"
                :key="album.id"
                class="album-card"
            >

                <img
                    :src="album.coverImage"
                    class="album-cover"
                    alt=""
                >

                <h3>
                    {{ album.title }}
                </h3>

                <p>
                    {{ album.artist }}
                </p>

            </div>

        </div>

    </section>

    

    <section>

        <h2 class="section-title">
            🎧 Recently Added
        </h2>

        <div class="songs-list">

            <div
                v-for="song in songs.slice(0, 5)"
                :key="song.id"
                class="recent-song"
            >

                <div>

                    <strong>
                        {{ song.title }}
                    </strong>

                    <p>
                        {{ song.artist }}
                    </p>

                </div>

                <span>
                    {{ song.albumTitle }}
                </span>

            </div>

        </div>

    </section>

    

    <div class="quote-card">

        <h2>
            Music Quote
        </h2>

        <p>
            "{{ randomQuote }}"
        </p>

    </div>

</div>

</template>

<style scoped>

.home-page {

    padding: 40px;

    color: white;
}

.hero-card {

    background:
        linear-gradient(
            135deg,
            #312e81,
            #4338ca
        );

    padding: 40px;

    border-radius: 24px;

    margin-bottom: 35px;

    box-shadow:
        0 0 30px rgba(99,102,241,.25);
}

.hero-card h1 {

    font-size: 3rem;

    margin-bottom: 15px;
}

.role-badge {

    background: rgba(255,255,255,.15);

    padding: 10px 18px;

    border-radius: 999px;

    font-weight: 700;
}

.stats-grid {

    display: grid;

    grid-template-columns:
        repeat(3, 1fr);

    gap: 25px;

    margin-bottom: 45px;
}

.stat-card {

    background: #111111;

    border-radius: 20px;

    padding: 30px;

    text-align: center;

    transition: .25s;
}

.stat-card:hover {

    transform:
        translateY(-6px);
}

.stat-icon {

    font-size: 2rem;

    margin-bottom: 12px;
}

.stat-card h2 {

    font-size: 2rem;

    margin-bottom: 8px;
}

.stat-card p {

    color: #94a3b8;
}

.section-title {

    margin-bottom: 25px;

    font-size: 1.8rem;
}

.albums-row {

    display: flex;

    gap: 30px;

    margin-bottom: 50px;
}

.album-card {

    width: 220px;

    text-align: center;
}

.album-cover {

    width: 180px;

    height: 180px;

    border-radius: 50%;

    object-fit: cover;

    border: 8px solid #111827;

    transition: 1s linear;
}

.album-cover:hover {

    transform: rotate(360deg);
}

.album-card h3 {

    margin-top: 18px;
}

.album-card p {

    color: #94a3b8;
}

.songs-list {

    display: flex;

    flex-direction: column;

    gap: 15px;

    margin-bottom: 45px;
}

.recent-song {

    background: #111111;

    border-radius: 16px;

    padding: 18px 24px;

    display: flex;

    justify-content: space-between;

    align-items: center;
}

.recent-song p {

    color: #94a3b8;
}

.quote-card {

    background: #111111;

    border-radius: 20px;

    padding: 30px;

    text-align: center;
}

.quote-card h2 {

    margin-bottom: 15px;
}

.quote-card p {

    color: #cbd5e1;

    font-size: 1.1rem;
}

</style>