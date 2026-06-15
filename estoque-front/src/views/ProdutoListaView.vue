<script setup>
import { onMounted } from 'vue';
import { useListagemPaginada } from '../composables/useListagemPaginada';
import { useFiltro } from '../composables/useFiltro';
import { buscarProdutosPaginados, excluirProduto } from '../services/produtoService';
import ProdutoLista from '../components/ProdutoLista.vue';

const { filtros, limpar: limparFiltros } = useFiltro({ 
  nome: '', 
  categoriaId: null, 
  precoMin: null, 
  precoMax: null 
});

const {
  itens, carregando, erro, vazio, pagina, numeroHumano,
  carregar, proxima, anterior, reiniciar, mudarTamanho
} = useListagemPaginada(
  (opcoes) => buscarProdutosPaginados({ 
    page: opcoes.page, 
    size: opcoes.size, 
    nome: filtros.nome,
    categoriaId: filtros.categoriaId,
    precoMin: filtros.precoMin,
    precoMax: filtros.precoMax
  }),
  { tamanhoInicial: 10 }
);

async function deletar(id) {
  if (confirm('Tem certeza?')) {
    await excluirProduto(id);
    carregar();
  }
}

onMounted(carregar);
</script>

<template>
  <ProdutoLista 
    :produtos="itens"
    :carregando="carregando"
    :erro="erro"
    :vazio="vazio"
    :pagina="pagina"
    :numeroHumano="numeroHumano"
    :filtros="filtros"
    @deletar="deletar"
    @buscar="reiniciar"
    @proxima="proxima"
    @anterior="anterior"
    @mudarTamanho="mudarTamanho"
    @limparFiltros="limparFiltros"
  />
</template>