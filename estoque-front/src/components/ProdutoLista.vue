<script setup>
import PainelLista from './PainelLista.vue';
import ProdutoCard from './ProdutoCard.vue';
import ProdutoPaginacao from './ProdutoPaginacao.vue';
import ProdutoFiltros from '../components/ProdutoFiltros.vue';

defineProps(['produtos', 'carregando', 'erro', 'vazio', 'pagina', 'numeroHumano', 'filtros']);
defineEmits(['deletar', 'buscar', 'proxima', 'anterior', 'limparFiltros', 'mudarTamanho']);
</script>

<template>
  <div class="produto-lista-view">
    
    <header class="page-header">
      <h2>Produtos</h2>
      <router-link to="/produtos/novo" class="botao botao-novo-produto">
        + Novo Produto
      </router-link>
    </header>

    <ProdutoFiltros 
      :filtros="filtros" 
      @buscar="$emit('buscar')" 
      @limpar="$emit('limparFiltros')" 
    />
    
    <hr class="header-divider" />

    <PainelLista :carregando="carregando" :erro="erro" :vazio="vazio">
      
      <div class="produto-grid">
        <ProdutoCard 
          v-for="produto in produtos" 
          :key="produto.id" 
          :produto="produto"
          @deletar="$emit('deletar', produto.id)" 
        />
      </div>

      <ProdutoPaginacao 
        v-if="pagina"
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
  </div>
</template>