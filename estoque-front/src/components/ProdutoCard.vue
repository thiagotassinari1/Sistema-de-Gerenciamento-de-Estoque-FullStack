<template>
    <article class="produto-card">
        <div>
            <header class="produto-card-header">
                <strong>{{ produto.nome }}</strong>
                <span class="badge badge-category">{{ produto.nomeCategoria || 'Sem categoria' }}</span>
            </header>

            <p>{{ produto.descricao || 'Produto sem descrição.' }}</p>

            <footer>
                <strong>{{ precoFormatado }}</strong>
                <div class="produto-estoque">
                    <span class="produto-estoque-valor">Estoque: {{ produto.estoque }}</span>
                    <span v-if="estoque === 0" class="badge badge-danger">Sem estoque</span>
                    <span v-else-if="estoque > 0 && estoque <= 5" class="badge badge-warning">Estoque baixo</span>
                </div>
            </footer>
        </div>

        <div class="produto-acoes">
            <router-link :to="`/produtos/${produto.id}/editar`">
                <button type="button">Editar</button>
            </router-link>
            <button type="button" class="btn-excluir" @click="$emit('deletar', produto.id)">Excluir</button>
        </div>
    </article>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({ produto: Object });
defineEmits(['deletar']);

const precoFormatado = computed(() => {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(props.produto.preco || 0);
});

const estoque = computed(() => Number(props.produto.estoque));
</script>