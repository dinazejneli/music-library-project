<script setup>

defineProps({
    album: {
        type: Object,
        required: true
    }
})

const emit = defineEmits([
    'edit',
    'delete'
])

</script>

<template>

    <div class="album-card">

        <div class="vinyl-wrapper">

            <img
                :src="album.coverImage"
                :alt="album.title"
                class="album-cover"
            >

            <div class="vinyl-hole"></div>

        </div>

        <h2>
            {{ album.title }}
        </h2>

        <p>
            {{ album.artist }}
        </p>

        <div class="album-actions">

            <button
                class="edit-btn"
                @click="$emit('edit', album.id)"
            >
                Edit
            </button>

            <button
                class="delete-btn"
                @click="$emit('delete', album.id)"
            >
                Delete
            </button>

        </div>

    </div>

</template>

<style scoped>

.album-card {

    background: #111111;

    border-radius: 24px;

    padding: 25px;

    text-align: center;

    transition: .3s;
}

.album-card:hover {

    transform: translateY(-8px);
}

.vinyl-wrapper {

    position: relative;

    width: 230px;

    height: 230px;

    margin: 0 auto 20px;
}

.album-cover {

    width: 100%;

    height: 100%;

    border-radius: 50%;

    object-fit: cover;

    border: 8px solid #0f172a;

    transition: 1.5s linear;
}

.album-card:hover .album-cover {

    transform: rotate(360deg);
}

.vinyl-hole {

    position: absolute;

    width: 30px;

    height: 30px;

    background: white;

    border-radius: 50%;

    top: 50%;

    left: 50%;

    transform: translate(-50%, -50%);
}

h2 {

    color: white;

    margin-bottom: 8px;
}

p {

    color: #cbd5e1;

    margin-bottom: 20px;
}

.album-actions {

    display: flex;

    justify-content: center;

    gap: 12px;
}

.edit-btn {

    background: #2563eb;

    color: white;

    border: none;

    padding: 10px 18px;

    border-radius: 10px;

    cursor: pointer;
}

.delete-btn {

    background: #374151;

    color: white;

    border: none;

    padding: 10px 18px;

    border-radius: 10px;

    cursor: pointer;
}

</style>