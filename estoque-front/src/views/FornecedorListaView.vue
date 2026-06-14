<script setup>
import { onMounted } from 'vue'
import { useListagem } from '../composables/useListagem.js'
import { listarFornecedores, excluirFornecedor } from '../services/fornecedorService.js'

const {
    itens: fornecedores,
    carregando,
    erro,
    vazio,
    total,
    carregar,
} = useListagem(listarFornecedores)

async function deletar(id) {
    if (!confirm('Tem certeza que deseja excluir este fornecedor?')) return
    try {
        await excluirFornecedor(id)
        carregar()
    } catch (e) {
        alert('Não foi possível excluir. Verifique se o fornecedor não possui pedidos vinculados.')
    }
}

onMounted(carregar)
</script>

<template>
    <section class="lista-bloco">
        <header class="toolbar">
            <div>
                <h2>Fornecedores</h2>
                <p class="toolbar-info">{{ total }} fornecedor(es) cadastrado(s)</p>
            </div>
            <div class="toolbar-acoes">
                <router-link to="/fornecedores/novo">
                    <button type="button">Novo Fornecedor</button>
                </router-link>
                <button type="button" @click="carregar">Atualizar</button>
            </div>
        </header>

        <p v-if="carregando" class="status">Carregando...</p>
        <p v-else-if="erro" class="status status-error">{{ erro }}</p>
        <p v-else-if="vazio" class="status">Nenhum fornecedor cadastrado.</p>

        <ul v-else class="categoria-lista">
            <li v-for="fornecedor in fornecedores" :key="fornecedor.id">
                <div>
                    <strong>{{ fornecedor.nome }}</strong>
                    <span> — CNPJ: {{ fornecedor.cnpj }}</span>
                </div>
                <div class="toolbar-acoes">
                    <router-link :to="`/fornecedores/${fornecedor.id}/editar`">
                        <button type="button">Editar</button>
                    </router-link>
                    <button type="button" @click="deletar(fornecedor.id)">Excluir</button>
                </div>
            </li>
        </ul>
    </section>
</template>
