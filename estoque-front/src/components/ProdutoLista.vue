<script setup>
import PainelLista from './PainelLista.vue';
import ProdutoCard from './ProdutoCard.vue';
import ProdutoFiltros from './ProdutoFiltros.vue';
import ProdutoPaginacao from './ProdutoPaginacao.vue';

defineProps(['produtos', 'carregando', 'erro', 'vazio', 'pagina', 'numeroHumano', 'filtros']);
defineEmits(['deletar', 'buscar', 'proxima', 'anterior', 'limparFiltros', 'mudarTamanho']);
</script>

<template>
  <PainelLista 
    titulo="Produtos" 
    :total="pagina.totalItens" 
    :carregando="carregando" 
    :erro="erro" 
    :vazio="vazio"
    @atualizar="$emit('buscar')"
  >
    <template #filtros>
      <ProdutoFiltros :filtros="filtros" @buscar="$emit('buscar')" @limpar="$emit('limparFiltros')" />
    </template>

    <div class="produto-grid">
      <ProdutoCard 
        v-for="produto in produtos" 
        :key="produto.id" 
        :produto="produto" 
        @deletar="$emit('deletar', produto.id)"
      />
    </div>

    <ProdutoPaginacao 
      :numero-humano="numeroHumano"
      :total-paginas="pagina.totalPaginas"
      :total-itens="pagina.totalItens"
      :itens-exibidos="produtos.length"
      :tamanho="pagina.tamanho"
      :primeira="pagina.primeira"
      :ultima="pagina.ultima"
      :desabilitado="carregando"
      @anterior="$emit('anterior')"
      @proxima="$emit('proxima')"
      @mudar-tamanho="$emit('mudarTamanho', $event)"
    />
  </PainelLista>
</template>