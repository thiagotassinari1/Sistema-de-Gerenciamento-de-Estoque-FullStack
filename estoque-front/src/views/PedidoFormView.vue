<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { cadastrarPedido } from '../services/pedidoService.js'
import { buscarProdutosPaginados } from '../services/produtoService.js'
import { listarFornecedores } from '../services/fornecedorService.js'

const router = useRouter()

const pedido = ref({ quantidade: '', produtoId: '', fornecedorId: '' })
const produtos = ref([])
const fornecedores = ref([])

const tentouEnviar = ref(false)
const salvando = ref(false)
const carregandoOpcoes = ref(false)
const erroFormulario = ref('')
const mensagemSucesso = ref('')

const erros = computed(() => {
    const e = {}

    const quantidade = Number(pedido.value.quantidade)
    if (pedido.value.quantidade === '' || pedido.value.quantidade === null)
        e.quantidade = 'Informe a quantidade do pedido.'
    else if (!Number.isInteger(quantidade) || quantidade <= 0)
        e.quantidade = 'A quantidade deve ser um número inteiro maior que zero.'

    if (!pedido.value.produtoId)
        e.produtoId = 'Selecione um produto.'

    if (!pedido.value.fornecedorId)
        e.fornecedorId = 'Selecione um fornecedor.'

    return e
})

const formularioValido = computed(() => Object.keys(erros.value).length === 0)

// Normaliza a resposta de produtos paginados, aceitando tanto um array
// quanto um objeto paginado (ex.: { content: [...] })
function extrairLista(dados) {
    if (Array.isArray(dados)) return dados
    if (dados && Array.isArray(dados.content)) return dados.content
    if (dados && Array.isArray(dados.items)) return dados.items
    if (dados && Array.isArray(dados.data)) return dados.data
    return []
}

async function carregarOpcoes() {
    carregandoOpcoes.value = true
    erroFormulario.value = ''
    try {
        const [produtosResp, fornecedoresResp] = await Promise.all([
            buscarProdutosPaginados({ page: 0, size: 1000 }),
            listarFornecedores(),
        ])
        produtos.value = extrairLista(produtosResp)
        fornecedores.value = extrairLista(fornecedoresResp)
    } catch (e) {
        console.error(e)
        erroFormulario.value = 'Erro ao carregar produtos e fornecedores. Verifique se o backend está no ar.'
    } finally {
        carregandoOpcoes.value = false
    }
}

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
        const payload = {
            quantidade: Number(pedido.value.quantidade),
            produtoId: Number(pedido.value.produtoId),
            fornecedorId: Number(pedido.value.fornecedorId),
        }

        await cadastrarPedido(payload)
        mensagemSucesso.value = 'Pedido cadastrado com sucesso!'
        pedido.value = { quantidade: '', produtoId: '', fornecedorId: '' }
        tentouEnviar.value = false
    } catch (e) {
        console.error(e)
        erroFormulario.value = 'Não foi possível salvar. Verifique os dados informados.'
    } finally {
        salvando.value = false
    }
}

onMounted(carregarOpcoes)
</script>

<template>
    <section class="form-panel">
        <div class="section-title">
            <h2>Novo Pedido</h2>
            <p>Preencha os campos abaixo para registrar um novo pedido.</p>
        </div>
        <form class="produto-form" @submit.prevent="salvar">
            <label>
                Produto *
                <select
                    v-model="pedido.produtoId"
                    :class="{ 'input-error': tentouEnviar && erros.produtoId }"
                    :disabled="carregandoOpcoes"
                >
                    <option value="" disabled>Selecione um produto...</option>
                    <option v-for="prod in produtos" :key="prod.id" :value="prod.id">
                        {{ prod.nome }}
                    </option>
                </select>
                <span v-if="tentouEnviar && erros.produtoId" class="field-error">{{ erros.produtoId }}</span>
            </label>

            <label>
                Fornecedor *
                <select
                    v-model="pedido.fornecedorId"
                    :class="{ 'input-error': tentouEnviar && erros.fornecedorId }"
                    :disabled="carregandoOpcoes"
                >
                    <option value="" disabled>Selecione um fornecedor...</option>
                    <option v-for="forn in fornecedores" :key="forn.id" :value="forn.id">
                        {{ forn.nome }}
                    </option>
                </select>
                <span v-if="tentouEnviar && erros.fornecedorId" class="field-error">{{ erros.fornecedorId }}</span>
            </label>

            <label>
                Quantidade *
                <input
                    v-model="pedido.quantidade"
                    type="number"
                    min="1"
                    step="1"
                    placeholder="Ex.: 10"
                    :class="{ 'input-error': tentouEnviar && erros.quantidade }"
                />
                <span v-if="tentouEnviar && erros.quantidade" class="field-error">{{ erros.quantidade }}</span>
            </label>

            <p v-if="erroFormulario" class="form-error">{{ erroFormulario }}</p>
            <p v-if="mensagemSucesso" class="form-success">{{ mensagemSucesso }}</p>

            <div class="form-buttons">
                <button type="submit" :disabled="salvando || carregandoOpcoes">
                    {{ salvando ? 'Salvando...' : 'Salvar Pedido' }}
                </button>
                <button type="button" class="btn-cancelar" @click="router.push('/pedidos')">
                    Cancelar
                </button>
            </div>
        </form>
    </section>
</template>
