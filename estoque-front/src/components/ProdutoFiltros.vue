<template>
  <div class="toolbar-acoes">
    <input 
      type="text" 
      :value="modelValue" 
      @input="$emit('update:modelValue', $event.target.value)"
      @keyup.enter="$emit('buscar')"
      placeholder="Buscar por nome..."
    />
    <button @click="$emit('buscar')">Buscar</button>
  </div>
</template>

<script setup>
// Definimos a prop modelValue que o v-model do pai envia
defineProps(['modelValue'])
// Definimos os eventos que o componente dispara
defineEmits(['update:modelValue', 'buscar'])
</script>