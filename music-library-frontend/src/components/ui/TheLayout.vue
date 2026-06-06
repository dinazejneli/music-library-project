<template>

    <div>

        <TheNavBar />

        <main class="page">

            <slot />

        </main>

    </div>

</template>

<script setup>

import TheNavBar from './TheNavBar.vue'

</script>

<style scoped>

.page {

    padding: 24px;
}

</style>