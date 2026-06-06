<script setup>

import { ref, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import client from '@/helpers/client'

const songs = ref([])

const currentSong = ref(null)

const progress = ref(0)

const isPlaying = ref(false)

let timer = null

async function loadSongs() {

    try {

        const response =
            await client.get('/songs')

        songs.value =
            response.data

    } catch (error) {

        console.error(error)
    }
}

onMounted(loadSongs)

async function deleteSong(id) {

    if (!confirm('Delete this song?')) {
        return
    }

    try {

        await client.delete(`/songs/${id}`)

        await loadSongs()

    } catch (error) {

        alert(
            error.response?.data ||
            'Delete failed'
        )
    }
}

function formatDuration(seconds) {

    if (!seconds) {
        return '0:00'
    }

    const minutes =
        Math.floor(seconds / 60)

    const remaining =
        seconds % 60

    return `${minutes}:${remaining
        .toString()
        .padStart(2, '0')}`
}

function playSong(song) {

    currentSong.value = song

    progress.value = 0

    isPlaying.value = true

    clearInterval(timer)

    timer = setInterval(() => {

        if (!isPlaying.value) {
            return
        }

        progress.value +=
            100 / song.duration

        if (progress.value >= 100) {

            progress.value = 100

            clearInterval(timer)
        }

    }, 1000)
}

function togglePlay() {

    isPlaying.value =
        !isPlaying.value
}

function closePlayer() {

    currentSong.value = null

    progress.value = 0

    isPlaying.value = false

    clearInterval(timer)
}

</script>

<template>

    <div class="songs-page">

        <div class="page-header">

            <h1>Songs</h1>

            <RouterLink
                to="/songs/create"
                class="add-btn"
            >
                + Add Song
            </RouterLink>

        </div>

        <div class="songs-grid">

            <div
                v-for="song in songs"
                :key="song.id"
                :class="[
    'song-card',
    {
        playing:
        currentSong?.id === song.id
    }
]"
                @click="playSong(song)"
            >

                <div class="cover-wrapper">

                    <img
                        v-if="song.albumCover"
                        :src="song.albumCover"
                        :alt="song.albumTitle"
                        class="song-cover"
                    >

                    <div
                        v-else
                        class="cover-placeholder"
                    >
                        🎵
                    </div>

                    <div class="play-button">
                        ▶
                    </div>

                </div>

                <div class="song-content">

                    <h3>
                        {{ song.title }}
                    </h3>

                    <p class="artist">
                        {{ song.artist }}
                    </p>

                    <p class="album">
                        {{ song.albumTitle || 'Single' }}
                    </p>

                </div>

                <div
                    class="card-actions"
                    @click.stop
                >

                    <RouterLink
                        :to="`/songs/edit/${song.id}`"
                        class="edit-btn"
                    >
                        ✏
                    </RouterLink>

                    <button
                        class="delete-btn"
                        @click="deleteSong(song.id)"
                    >
                        🗑
                    </button>

                </div>

            </div>

        </div>

        <!-- NOW PLAYING -->

        <div
    v-if="currentSong"
    class="player-bar"
>

    <div class="player-left">

        <img
            v-if="currentSong.albumCover"
            :src="currentSong.albumCover"
            class="player-cover"
        >

        <div
            v-else
            class="player-placeholder"
        >
            🎵
        </div>

        <div>

            <h4>
                {{ currentSong.title }}
            </h4>

            <p>
                {{ currentSong.artist }}
            </p>

        </div>

    </div>

    <div class="player-center">

        <button
            class="player-control"
            @click="togglePlay"
        >
            {{ isPlaying ? '⏸' : '▶' }}
        </button>

        <div
    :class="[
        'equalizer',
        {
            paused: !isPlaying
        }
    ]"
>

            <span></span>
            <span></span>
            <span></span>

        </div>

        <div class="progress-container">

            <span>
    {{ formatDuration(
        Math.floor(
            currentSong.duration *
            (progress / 100)
        )
    ) }}
</span>

            <div class="progress-bar">

                <div
                    class="progress"
                    :style="{
                        width:
                        progress + '%'
                    }"
                ></div>

            </div>

            <span>
                {{ formatDuration(
                    currentSong.duration
                ) }}
            </span>

        </div>

    </div>

    <button
        class="close-player"
        @click="closePlayer"
    >
        ✕
    </button>

</div>

    </div>

</template>

<style scoped>

.songs-page {

    padding: 40px;
}

.page-header {

    display: flex;

    justify-content: space-between;

    align-items: center;

    margin-bottom: 40px;
}

.page-header h1 {

    color: white;

    font-size: 3rem;
}

.add-btn {

    background: #22c55e;

    color: white;

    padding: 12px 22px;

    border-radius: 12px;

    font-weight: 700;

    transition: .2s;
}

.add-btn:hover {

    transform: translateY(-2px);
}

.songs-grid {

    display: grid;

    grid-template-columns:
            repeat(auto-fill, minmax(280px, 1fr));

    gap: 30px;
}

.song-card {

    background: #111111;

    border-radius: 18px;

    overflow: hidden;

    cursor: pointer;

    transition: .25s;

    position: relative;
}

.song-card:hover {

    transform: translateY(-8px);

    background: #171717;
}

.song-card.playing {

    border: 2px solid #22c55e;

    box-shadow:
            0 0 35px rgba(34,197,94,.55);

    transform:
            translateY(-8px);
}

.cover-wrapper {

    position: relative;
}

.song-cover {

    width: 100%;

    height: 280px;

    object-fit: cover;
}

.cover-placeholder {

    height: 280px;

    display: flex;

    align-items: center;

    justify-content: center;

    background: #1f2937;

    font-size: 5rem;
}

.play-button {

    position: absolute;

    bottom: 15px;

    right: 15px;

    width: 60px;

    height: 60px;

    background: #22c55e;

    border-radius: 50%;

    display: flex;

    align-items: center;

    justify-content: center;

    color: black;

    font-size: 1.4rem;

    font-weight: bold;

    opacity: 0;

    transform:
            translateY(10px);

    transition: .25s;
}

.song-card:hover .play-button {

    opacity: 1;

    transform:
            translateY(0);
}

.song-content {

    padding: 20px;
}

.song-content h3 {

    color: white;

    font-size: 1.4rem;

    margin-bottom: 8px;
}

.artist {

    color: #d1d5db;

    margin-bottom: 6px;
}

.album {

    color: #94a3b8;
}

.card-actions {

    position: absolute;

    top: 15px;

    right: 15px;

    display: flex;

    gap: 10px;

    opacity: 0;

    transition: .2s;
}

.song-card:hover .card-actions {

    opacity: 1;
}

.edit-btn,
.delete-btn {

    width: 40px;

    height: 40px;

    border: none;

    border-radius: 50%;

    display: flex;

    align-items: center;

    justify-content: center;

    background: rgba(0,0,0,.75);

    color: white;

    cursor: pointer;
}

/* PLAYER */

.player-bar {

    position: fixed;

    bottom: 0;

    left: 0;

    right: 0;

    height: 110px;

    background: #08152f;

    border-top: 1px solid #374151;

    display: grid;

    grid-template-columns: 280px 1fr 80px;

    align-items: center;

    padding: 0 30px;

    z-index: 999;
}

.player-left {

    display: flex;

    align-items: center;

    gap: 15px;
}

.player-cover,
.player-placeholder {

    width: 60px;

    height: 60px;

    border-radius: 8px;

    object-fit: cover;

    background: #1f2937;

    display: flex;

    align-items: center;

    justify-content: center;
}

.player-left h4 {

    color: white;

    margin-bottom: 4px;
}

.player-left p {

    color: #9ca3af;
}

.player-center {

    display: flex;

    flex-direction: column;

    align-items: center;

    justify-content: center;

    width: 100%;
}

.player-control {

    width: 60px;

    height: 60px;

    border-radius: 50%;

    border: none;

    background: #22c55e;

    color: black;

    font-size: 1.5rem;

    font-weight: bold;

    cursor: pointer;

    margin-bottom: 12px;
}

.player-control:hover {

    transform: scale(1.05);
}

.progress-container {

    display: flex;

    align-items: center;

    gap: 12px;

    width: 650px;

    max-width: 90%;
}

.time {

    color: white;

    font-size: 1rem;

    font-weight: 700;

    min-width: 45px;

    text-align: center;
}

.progress-bar {

    flex: 1;

    height: 6px;

    background: #475569;

    border-radius: 999px;

    overflow: hidden;
}

.progress {

    height: 100%;

    background: #22c55e;

    border-radius: 999px;
}

.song-card.playing {

    border: 2px solid #22c55e;

    box-shadow:
        0 0 25px rgba(34,197,94,.45);
}


</style>