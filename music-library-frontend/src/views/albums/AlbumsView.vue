<script setup>

import { ref, onMounted } from 'vue'
import { RouterLink, useRouter } from 'vue-router'

import client from '@/helpers/client'
import AlbumCard from '@/components/AlbumCard.vue'

const router = useRouter()

const albums = ref([])

async function loadAlbums() {

    try {

        const response =
            await client.get('/albums')

        albums.value = response.data

    } catch (error) {

        console.error(error)
    }
}

function editAlbum(id) {

    router.push(
        `/albums/edit/${id}`
    )
}

async function deleteAlbum(id) {

    if (!confirm('Delete this album?')) {
        return
    }

    try {

        await client.delete(
            `/albums/${id}`
        )

        await loadAlbums()

    } catch (error) {

        alert(
            error.response?.data ||
            'Delete failed'
        )
    }
}

onMounted(loadAlbums)

</script>

<template>

    <div class="albums-page">

        <div class="page-header">

            <h1>
                Albums
            </h1>

            <RouterLink
                to="/albums/create"
                class="add-btn"
            >
                + Add Album
            </RouterLink>

        </div>

        <div class="albums-grid">

            <AlbumCard

                v-for="album in albums"

                :key="album.id"

                :album="album"

                @edit="editAlbum"

                @delete="deleteAlbum"

            />

        </div>

    </div>

</template>

<style scoped>

.albums-page {

    padding: 35px;
}

.page-header {

    display: flex;

    justify-content: space-between;

    align-items: center;

    margin-bottom: 40px;
}

.page-header h1 {

    margin: 0;

    font-size: 3rem;

    font-weight: 800;
}

.add-btn {

    background: #1db954;

    color: white;

    padding: 12px 24px;

    border-radius: 999px;

    font-weight: 700;

    transition: .2s;
}

.add-btn:hover {

    transform: scale(1.05);
}

.albums-grid {

    display: grid;

    grid-template-columns:
        repeat(auto-fill, minmax(320px, 1fr));

    gap: 40px;
}

</style>