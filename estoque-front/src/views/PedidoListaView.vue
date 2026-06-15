<script setup>
import { onMounted } from 'vue'
import { useListagem } from '../composables/useListagem.js'
import { listarPedidos, excluirPedido } from '../services/pedidoService.js'

const {
    itens: pedidos,
    carregando,
    erro,
    vazio,
    total,
    carregar,
} = useListagem(listarPedidos)

async function deletar(id) {
    if (!confirm('Tem certeza que deseja excluir este pedido?')) return
    try {
        await excluirPedido(id)
        carregar()
    } catch (e) {
        alert('Não foi possível excluir o pedido.')
    }
}

onMounted(carregar)
</script>

<template>
    <section class="lista-bloco">
        <header class="toolbar">
            <div>
                <h2>Pedidos</h2>
                <p class="toolbar-info">{{ total }} pedido(s) cadastrado(s)</p>
            </div>
            <div class="toolbar-acoes">
                <router-link to="/pedidos/novo">
                    <button type="button">Novo Pedido</button>
                </router-link>
                <button type="button" @click="carregar">Atualizar</button>
            </div>
        </header>
        <p v-if="carregando" class="status">Carregando...</p>
        <p v-else-if="erro" class="status status-error">{{ erro }}</p>
        <p v-else-if="vazio" class="status">Nenhum pedido cadastrado.</p>
        <ul v-else class="categoria-lista">
            <li v-for="pedido in pedidos" :key="pedido.id">
                <div>
                    <strong>{{ pedido.nomeProduto }}</strong>
                    <span> — Fornecedor: {{ pedido.nomeFornecedor }}</span>
                    <br />
                    <span>Quantidade: {{ pedido.quantidade }} | Status: {{ pedido.status }} | Data: {{ pedido.data }}</span>
                </div>
                <div class="toolbar-acoes">
                    <button type="button" @click="deletar(pedido.id)">Excluir</button>
                </div>
            </li>
        </ul>
    </section>
</template>
