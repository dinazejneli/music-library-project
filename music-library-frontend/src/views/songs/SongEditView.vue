```vue
<script setup>

import { reactive, ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import client from '@/helpers/client'

const route = useRoute()
const router = useRouter()

const albums = ref([])

const song = reactive({
    title: '',
    artist: '',
    durationFormatted: '',
    albumId: null
})

const errorMessage = ref('')

const selectedAlbum = computed(() => {

    return albums.value.find(
        album => album.id === song.albumId
    )
})

async function loadAlbums() {

    const response =
        await client.get('/albums')

    albums.value = response.data
}

async function loadSong() {

    const response =
        await client.get(
            `/songs/${route.params.id}`
        )

    song.title =
        response.data.title

    song.artist =
        response.data.artist

    song.durationFormatted =
        response.data.durationFormatted

    song.albumId =
        response.data.albumId
}

async function updateSong() {

    try {

        await client.put(
            `/songs/${route.params.id}`,
            song
        )

        router.push('/songs')

    } catch (error) {

        errorMessage.value =
            error.response?.data ||
            'Failed to update song'
    }
}

onMounted(async () => {

    await loadAlbums()

    await loadSong()
})

</script>

<template>

<div class="page">

    <div class="song-card">

        <h1>
            Edit Song
        </h1>

        <p class="subtitle">
            Update song information
        </p>

        <form @submit.prevent="updateSong">

            <div class="form-group">

                <label>
                    Song Title
                </label>

                <input
                    v-model="song.title"
                    placeholder="Enter song title"
                    required
                >

            </div>

            <div class="form-group">

                <label>
                    Artist
                </label>

                <input
                    v-model="song.artist"
                    placeholder="Enter artist"
                    required
                >

            </div>

            <div class="form-group">

                <label>
                    Duration
                </label>

                <input
                    v-model="song.durationFormatted"
                    placeholder="03:45"
                    required
                >

            </div>

            <div class="form-group">

                <label>
                    Album
                </label>

                <select
                    v-model="song.albumId"
                    class="album-select"
                    required
                >

                    <option
                        v-for="album in albums"
                        :key="album.id"
                        :value="album.id"
                    >
                        {{ album.title }}
                    </option>

                </select>

            </div>

            <div
                v-if="selectedAlbum?.coverImage"
                class="preview-section"
            >

                <p>
                    Album Preview
                </p>

                <img
                    :src="selectedAlbum.coverImage"
                    class="preview-image"
                >

            </div>

            <div
                v-if="errorMessage"
                class="error"
            >
                {{ errorMessage }}
            </div>

            <div class="buttons">

                <button
                    type="submit"
                    class="save-btn"
                >
                    Save Changes
                </button>

                <button
                    type="button"
                    class="cancel-btn"
                    @click="router.push('/songs')"
                >
                    Cancel
                </button>

            </div>

        </form>

    </div>

</div>

</template>

<style scoped>

.page {

    min-height: calc(100vh - 90px);

    display: flex;

    justify-content: center;

    align-items: center;

    padding: 40px;
}

.song-card {

    width: 100%;

    max-width: 560px;

    background: #111111;

    border-radius: 24px;

    padding: 40px;

    box-shadow:
        0 0 40px rgba(34,197,94,.18);
}

h1 {

    color: white;

    text-align: center;

    margin-bottom: 10px;

    font-size: 2.5rem;
}

.subtitle {

    text-align: center;

    color: #94a3b8;

    margin-bottom: 35px;
}

.form-group {

    margin-bottom: 22px;
}

label {

    display: block;

    color: white;

    margin-bottom: 8px;

    font-weight: 600;
}

input,
select {

    width: 100%;

    padding: 15px;

    border: none;

    border-radius: 12px;

    background: #1f2937;

    color: white;

    font-size: 1rem;
}

.album-select {
    width: 100%;

    padding: 15px 50px 15px 15px;

    border: none;

    border-radius: 12px;

    background: #1f2937;

    color: white;

    font-size: 1rem;

    appearance: none;

    -webkit-appearance: none;

    -moz-appearance: none;

    background-image:
        url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='white' viewBox='0 0 16 16'%3E%3Cpath d='M1.5 5.5L8 12l6.5-6.5' stroke='white' stroke-width='2' fill='none'/%3E%3C/svg%3E");

    background-repeat: no-repeat;

    background-position: right 18px center;

    background-size: 14px;
}

input:focus,
select:focus {

    outline: none;

    box-shadow:
        0 0 0 2px #22c55e;
}

.preview-section {

    text-align: center;

    margin-top: 25px;
}

.preview-section p {

    color: #cbd5e1;

    margin-bottom: 12px;
}

.preview-image {

    width: 180px;

    height: 180px;

    object-fit: cover;

    border-radius: 50%;

    border: 8px solid #0f172a;

    transition: 1s linear;
}

.preview-image:hover {

    transform: rotate(360deg);
}

.error {

    margin-top: 15px;

    color: #ef4444;

    text-align: center;
}

.buttons {

    display: flex;

    gap: 15px;

    margin-top: 30px;
}

.save-btn {

    flex: 1;

    background:
        linear-gradient(
            135deg,
            #16a34a,
            #22c55e
        );

    color: white;

    border: none;

    border-radius: 12px;

    padding: 14px;

    font-weight: 700;

    cursor: pointer;
}

.save-btn:hover {

    transform: translateY(-3px);
}

.cancel-btn {

    flex: 1;

    background: #374151;

    color: white;

    border: none;

    border-radius: 12px;

    padding: 14px;

    font-weight: 700;

    cursor: pointer;
}

.cancel-btn:hover {

    background: #4b5563;
}

</style>
