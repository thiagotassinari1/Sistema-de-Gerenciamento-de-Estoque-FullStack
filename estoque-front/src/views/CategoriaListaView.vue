<script setup>
import { onMounted } from 'vue'
import { useListagem } from '../composables/useListagem.js'
import { listarCategorias, excluirCategoria } from '../services/categoriaService.js'

const {
    itens: categorias,
    carregando,
    erro,
    vazio,
    total,
    carregar,
} = useListagem(listarCategorias)

async function deletar(id) {
    if (!confirm('Tem certeza que deseja excluir esta categoria?')) return
    try {
        await excluirCategoria(id)
        carregar()
    } catch (e) {
        alert('Não foi possível excluir. Verifique se a categoria não possui produtos vinculados.')
    }
}

onMounted(carregar)
</script>

<template>
    <section class="lista-bloco">
        <header class="toolbar">
            <div>
                <h2>Categorias</h2>
                <p class="toolbar-info">{{ total }} categoria(s) cadastrada(s)</p>
            </div>
            <div class="toolbar-acoes">
                <router-link to="/categorias/nova">
                    <button type="button">Nova Categoria</button>
                </router-link>
                <button type="button" @click="carregar">Atualizar</button>
            </div>
        </header>

        <p v-if="carregando" class="status">Carregando...</p>
        <p v-else-if="erro" class="status status-error">{{ erro }}</p>
        <p v-else-if="vazio" class="status">Nenhuma categoria cadastrada.</p>

        <ul v-else class="categoria-lista">
            <li v-for="categoria in categorias" :key="categoria.id">
                <div>
                    <strong>{{ categoria.nome }}</strong>
                    <span v-if="categoria.descricao"> — {{ categoria.descricao }}</span>
                </div>
                <div class="toolbar-acoes">
                    <router-link :to="`/categorias/${categoria.id}/editar`">
                        <button type="button">Editar</button>
                    </router-link>
                    <button type="button" @click="deletar(categoria.id)">Excluir</button>
                </div>
            </li>
        </ul>
    </section>
</template>
