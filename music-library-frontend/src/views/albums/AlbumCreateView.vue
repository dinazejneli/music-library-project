<script setup>

import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import client from '@/helpers/client'

const router = useRouter()

const album = reactive({
    title: '',
    artist: '',
    coverImage: ''
})

async function createAlbum() {

    try {

        await client.post(
            '/albums',
            album
        )

        router.push('/albums')

    } catch (error) {

        console.error(error)

        alert(
            error.response?.data ||
            'Failed to create album'
        )
    }
}

</script>

<template>

<div class="page">

    <div class="album-card">

        <h1>
            Create Album
        </h1>

        <p class="subtitle">
            Add a new album to your music library
        </p>

        <form @submit.prevent="createAlbum">

            <div class="form-group">

                <label>
                    Album Title
                </label>

                <input
                    v-model="album.title"
                    placeholder="Enter album title"
                    required
                >

            </div>

            <div class="form-group">

                <label>
                    Artist
                </label>

                <input
                    v-model="album.artist"
                    placeholder="Enter artist name"
                    required
                >

            </div>

            <div class="form-group">

                <label>
                    Cover Image URL
                </label>

                <input
                    v-model="album.coverImage"
                    placeholder="https://..."
                >

            </div>

            <div
                v-if="album.coverImage"
                class="preview-section"
            >

                <p>
                    Preview
                </p>

                <img
                    :src="album.coverImage"
                    class="preview-image"
                    alt="Album Cover"
                >

            </div>

            <div class="buttons">

                <button
                    type="submit"
                    class="create-btn"
                >
                    Create Album
                </button>

                <button
                    type="button"
                    class="cancel-btn"
                    @click="router.push('/albums')"
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

.album-card {

   width: 100%;

    max-width: 560px;

    background: #111111;

    border-radius: 24px;

    padding: 40px;

    box-shadow:
        0 0 40px rgba(99,102,241,.25);
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

.form {
    width: 100%;
    max-width: 100%;
}

.form-group {

    margin-bottom: 22px;

}

label {

    display: block;

    width: 100%;

    color: white;

    margin-bottom: 8px;

    font-weight: 600;

    text-align: left;
}

input {

    width: 100%;

    padding: 15px;

    border: none;

    border-radius: 12px;

    background: #1f2937;

    color: white;

    font-size: 1rem;
}

input:focus {

    outline: none;

    box-shadow:
        0 0 0 2px #6366f1;
}

.preview-section {

    margin-top: 20px;

    text-align: center;
}

.preview-section p {

    color: #cbd5e1;

    margin-bottom: 15px;
}

.preview-image {

    width: 220px;

    height: 220px;

    object-fit: cover;

    border-radius: 50%;

    border: 8px solid #0f172a;

    transition: 1s linear;
}

.preview-image:hover {

    transform: rotate(360deg);
}

.buttons {

    display: flex;

    justify-content: center;

    gap: 15px;

    margin-top: 30px;
}

.create-btn {

    flex: 1;

    background:
        linear-gradient(
            135deg,
            #4338ca,
            #6366f1
        );

    color: white;

    padding: 14px;

    border: none;

    border-radius: 12px;

    font-weight: 700;

    cursor: pointer;

    transition: .2s;
}

.create-btn:hover {

    transform: translateY(-3px);
}

.cancel-btn {

    flex: 1;

    background: #374151;

    color: white;

    padding: 14px;

    border: none;

    border-radius: 12px;

    font-weight: 700;

    cursor: pointer;
}

.cancel-btn:hover {

    background: #4b5563;
}

</style>