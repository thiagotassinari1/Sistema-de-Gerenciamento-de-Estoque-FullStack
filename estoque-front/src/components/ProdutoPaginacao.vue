<script setup>
defineProps({
    numeroHumano: { type: Number, required: true },
    totalPaginas: { type: Number, required: true },
    totalItens: { type: Number, required: true },
  itensExibidos: { type: Number, required: true },
    tamanho: { type: Number, required: true },
    primeira: { type: Boolean, required: true },
    ultima: { type: Boolean, required: true },
    desabilitado: { type: Boolean, required: true }
})

defineEmits(['anterior', 'proxima', 'mudar-tamanho'])

const tamanhos = [5, 10, 20, 50]
</script>

<template>
  <nav v-if="totalItens > 0" class="paginacao" aria-label="Paginação de produtos">
    <div class="paginacao-info">
      <span>
        Exibindo {{ itensExibidos }} de {{ totalItens }} produtos. 
      </span>
      <span>
        Página {{ numeroHumano }} de {{ totalPaginas }}.
      </span>
    </div>
    
    <div class="paginacao-controles">
      <label class="paginacao-tamanho">
        <span>Itens por página</span>
        <select 
            :value="tamanho"
            :disabled="desabilitado"
            @change="$emit('mudar-tamanho', Number($event.target.value))"
        >
            <option v-for="t in tamanhos" :key="t" :value="t">{{ t }}</option>
        </select>
      </label>

      <button type="button" :disabled="primeira || desabilitado" @click="$emit('anterior')">
        Anterior
      </button>
      <button type="button" :disabled="ultima || desabilitado" @click="$emit('proxima')">
        Próxima
      </button>
    </div>
  </nav>
</template>