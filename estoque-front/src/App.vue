<script setup>
</script>

<template>
  <header class="app-header">
    <div class="container header-content">
      <strong>Gestão de Estoque</strong>
      <nav class="toolbar-acoes">
        <router-link to="/produtos">Produtos</router-link>
        <router-link to="/categorias">Categorias</router-link>
      </nav>
    </div>
  </header>

  <main class="container page-title">
    <p class="eyebrow">Painel Administrativo</p>
    <router-view></router-view>
  </main>
</template>