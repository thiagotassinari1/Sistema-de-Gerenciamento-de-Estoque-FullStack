<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { buscarFornecedorPorId, cadastrarFornecedor, atualizarFornecedor } from '../services/fornecedorService.js'
 
const route = useRoute()
const router = useRouter()
 
const isEdicao = computed(() => !!route.params.id)
 
const fornecedor = ref({ nome: '', cnpj: '' })
const tentouEnviar = ref(false)
const salvando = ref(false)
const erroFormulario = ref('')
const mensagemSucesso = ref('')
 
const erros = computed(() => {
    const e = {}
 
    if (!fornecedor.value.nome.trim())
        e.nome = 'Informe o nome do fornecedor.'
    else if (fornecedor.value.nome.trim().length < 2)
        e.nome = 'O nome deve ter pelo menos 2 caracteres.'
 
    const cnpjLimpo = (fornecedor.value.cnpj ?? '').replace(/\D/g, '')
    if (!cnpjLimpo)
        e.cnpj = 'Informe o CNPJ do fornecedor.'
    else if (cnpjLimpo.length !== 14)
        e.cnpj = 'O CNPJ deve conter 14 dígitos.'
 
    return e
})
 
const formularioValido = computed(() => Object.keys(erros.value).length === 0)
 
function formatarCnpj(event) {
    const apenasNumeros = event.target.value.replace(/\D/g, '').slice(0, 14)
    fornecedor.value.cnpj = apenasNumeros
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
            nome: fornecedor.value.nome.trim(),
            cnpj: fornecedor.value.cnpj.replace(/\D/g, ''),
        }
 
        if (isEdicao.value) {
            await atualizarFornecedor(route.params.id, payload)
            mensagemSucesso.value = 'Fornecedor atualizado com sucesso!'
        } else {
            await cadastrarFornecedor(payload)
            mensagemSucesso.value = 'Fornecedor cadastrado com sucesso!'
            fornecedor.value = { nome: '', cnpj: '' }
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
        const dados = await buscarFornecedorPorId(route.params.id)
        fornecedor.value = { nome: dados.nome, cnpj: dados.cnpj ?? '' }
    } catch (e) {
        erroFormulario.value = 'Erro ao carregar os dados do fornecedor.'
    }
})
</script>
 
<template>
    <section class="form-panel">
        <div class="section-title">
            <h2>{{ isEdicao ? 'Editar Fornecedor' : 'Novo Fornecedor' }}</h2>
            <p>Preencha os campos abaixo para {{ isEdicao ? 'atualizar o' : 'cadastrar um novo' }} fornecedor.</p>
        </div>
        <form class="produto-form" @submit.prevent="salvar">
            <label>
                Nome *
                <input
                    v-model="fornecedor.nome"
                    type="text"
                    placeholder="Ex.: Distribuidora ABC Ltda"
                    :class="{ 'input-error': tentouEnviar && erros.nome }"
                />
                <span v-if="tentouEnviar && erros.nome" class="field-error">{{ erros.nome }}</span>
            </label>
            <label>
                CNPJ *
                <input
                    :value="fornecedor.cnpj"
                    @input="formatarCnpj"
                    type="text"
                    inputmode="numeric"
                    maxlength="14"
                    placeholder="Somente números, ex.: 12345678000199"
                    :class="{ 'input-error': tentouEnviar && erros.cnpj }"
                />
                <span v-if="tentouEnviar && erros.cnpj" class="field-error">{{ erros.cnpj }}</span>
            </label>
            <p v-if="erroFormulario" class="form-error">{{ erroFormulario }}</p>
            <p v-if="mensagemSucesso" class="form-success">{{ mensagemSucesso }}</p>
            <div class="form-buttons">
                <button type="submit" :disabled="salvando">
                    {{ salvando ? 'Salvando...' : 'Salvar Fornecedor' }}
                </button>
                <button type="button" class="btn-cancelar" @click="router.push('/fornecedores')">
                    Cancelar
                </button>
            </div>
        </form>
    </section>
</template>
