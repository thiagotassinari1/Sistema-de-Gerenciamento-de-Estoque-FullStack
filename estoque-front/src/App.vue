<script setup>
import AppHeader from './components/AppHeader.vue'
</script>

<template>
  <AppHeader />

  <main class="container page-title">
    <p class="eyebrow">Painel Administrativo</p>
    <router-view></router-view>
  </main>
</template>