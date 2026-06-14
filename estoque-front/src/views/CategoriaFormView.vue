<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { buscarCategoriaPorId, cadastrarCategoria, atualizarCategoria } from '../services/categoriaService.js'

const route = useRoute()
const router = useRouter()

const isEdicao = computed(() => !!route.params.id)

const categoria = ref({ nome: '', descricao: '' })
const tentouEnviar = ref(false)
const salvando = ref(false)
const erroFormulario = ref('')
const mensagemSucesso = ref('')

const erros = computed(() => {
    const e = {}
    if (!categoria.value.nome.trim())
        e.nome = 'Informe o nome da categoria.'
    else if (categoria.value.nome.trim().length < 2)
        e.nome = 'O nome deve ter pelo menos 2 caracteres.'
    return e
})

const formularioValido = computed(() => Object.keys(erros.value).length === 0)

async function salvar() {
    tentouEnviar.value = true
    erroFormulario.value = ''
    mensagemSucesso.value = ''

    if (!formularioValido.value) {
        erroFormulario.value = 'Revise os campos destacados antes de continuar.'
        return
    }

    salvando.value = true
    try {
        if (isEdicao.value) {
            await atualizarCategoria(route.params.id, categoria.value)
            mensagemSucesso.value = 'Categoria atualizada com sucesso!'
        } else {
            await cadastrarCategoria(categoria.value)
            mensagemSucesso.value = 'Categoria cadastrada com sucesso!'
            categoria.value = { nome: '', descricao: '' }
            tentouEnviar.value = false
        }
    } catch (e) {
        erroFormulario.value = 'Não foi possível salvar. Verifique os dados informados.'
    } finally {
        salvando.value = false
    }
}

onMounted(async () => {
    if (!isEdicao.value) return
    try {
        const dados = await buscarCategoriaPorId(route.params.id)
        categoria.value = { nome: dados.nome, descricao: dados.descricao ?? '' }
    } catch (e) {
        erroFormulario.value = 'Erro ao carregar os dados da categoria.'
    }
})
</script>

<template>
    <section class="form-panel">
        <div class="section-title">
            <h2>{{ isEdicao ? 'Editar Categoria' : 'Nova Categoria' }}</h2>
            <p>Preencha os campos abaixo para {{ isEdicao ? 'atualizar a' : 'cadastrar uma nova' }} categoria.</p>
        </div>

        <form class="produto-form" @submit.prevent="salvar">
            <label>
                Nome *
                <input
                    v-model="categoria.nome"
                    type="text"
                    placeholder="Ex.: Eletrônicos"
                    :class="{ 'input-error': tentouEnviar && erros.nome }"
                />
                <span v-if="tentouEnviar && erros.nome" class="field-error">{{ erros.nome }}</span>
            </label>

            <label>
                Descrição
                <textarea
                    v-model="categoria.descricao"
                    rows="3"
                    placeholder="Descrição opcional da categoria"
                />
            </label>

            <p v-if="erroFormulario" class="form-error">{{ erroFormulario }}</p>
            <p v-if="mensagemSucesso" class="form-success">{{ mensagemSucesso }}</p>

            <div class="form-buttons">
                <button type="submit" :disabled="salvando">
                    {{ salvando ? 'Salvando...' : 'Salvar Categoria' }}
                </button>
                <button type="button" class="btn-cancelar" @click="router.push('/categorias')">
                    Cancelar
                </button>
            </div>
        </form>
    </section>
</template>
