<template>
  <div class="lista-bloco">
    <div v-if="carregando" class="status">
      Carregando dados do servidor...
    </div>
    
    <div v-else-if="erro" class="status status-error">
      {{ erro }}
    </div>
    
    <div v-else-if="vazio" class="status">
      Nenhum registro encontrado no banco de dados.
    </div>
    
    <div v-else-if="semResultado" class="status">
      Nenhum registro corresponde aos filtros atuais.
    </div>
    
    <slot v-else></slot>
  </div>
</template>

<script setup>
defineProps({
  carregando: Boolean,
  erro: String,
  vazio: Boolean,
  semResultado: Boolean
})
</script>