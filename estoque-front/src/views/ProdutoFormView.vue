<template>
  <div class="form-panel">
    <div class="section-title">
      <h2>{{ isEdicao ? 'Editar Produto' : 'Novo Produto' }}</h2>
      <p>Preencha os campos abaixo para {{ isEdicao ? 'atualizar o' : 'cadastrar um novo' }} produto.</p>
    </div>

    <div v-if="mensagemErro" class="status status-error status-message">
      {{ mensagemErro }}
    </div>
    <div v-if="mensagemSucesso" class="status status-success">
      {{ mensagemSucesso }}
    </div>

    <form @submit.prevent="salvar" class="produto-form">
      
      <label>
        Nome do Produto *
        <input 
          type="text" 
          v-model="produto.nome" 
          required 
          placeholder="Ex: Teclado Mecânico" 
        />
      </label>

      <label>
        Descrição
        <textarea 
          v-model="produto.descricao" 
          rows="3" 
          placeholder="Detalhes do produto..."
        ></textarea>
      </label>

      <div class="form-grid">
        <label>
          Preço (R$) *
          <input 
            type="number" 
            step="0.01" 
            min="0" 
            v-model="produto.preco" 
            required 
          />
        </label>

        <label>
          Quantidade em Estoque *
          <input 
            type="number" 
            min="0" 
            v-model="produto.quantidade" 
            required 
          />
        </label>
      </div>

      <label>
        Categoria *
        <select v-model="produto.categoriaId" required>
          <option value="" disabled>Selecione uma categoria...</option>
          <option v-for="cat in categorias" :key="cat.id" :value="cat.id">
            {{ cat.nome }}
          </option>
        </select>
      </label>

      <div class="form-buttons">
        <button type="submit" :disabled="salvando">
          {{ salvando ? 'Salvando...' : 'Salvar Produto' }}
        </button>
        
        <router-link to="/produtos" class="btn-cancelar-link">
          <button type="button" class="btn-cancelar">
            Cancelar
          </button>
        </router-link>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { buscarProdutoPorId, cadastrarProduto, atualizarProduto } from '../services/produtoService'
import { listarCategorias } from '../services/categoriaService'

const route = useRoute()
const router = useRouter()

const isEdicao = computed(() => !!route.params.id)

const categorias = ref([])
const salvando = ref(false)
const mensagemErro = ref('')
const mensagemSucesso = ref('')

const produto = ref({
  nome: '',
  descricao: '',
  preco: '',
  quantidade: '', 
  categoriaId: ''
})

async function carregarCategorias() {
  try {
    const dados = await listarCategorias()
    categorias.value = Array.isArray(dados) ? dados : []
  } catch (e) {
    console.error(e)
    mensagemErro.value = 'Erro ao carregar as categorias. Verifique se o backend está no ar.'
  }
}

async function carregarProduto() {
  if (!isEdicao.value) return
  
  try {
    const dados = await buscarProdutoPorId(route.params.id)
    produto.value = {
      nome: dados.nome,
      descricao: dados.descricao,
      preco: dados.preco,
      quantidade: dados.estoque, 
      categoriaId: dados.categoriaId
    }
  } catch (e) {
    mensagemErro.value = 'Erro ao carregar os dados deste produto.'
  }
}

async function salvar() {
  salvando.value = true
  mensagemErro.value = ''
  mensagemSucesso.value = ''

  try {

    const payload = {
      ...produto.value,
      estoque: produto.value.quantidade
    }

    if (isEdicao.value) {
      await atualizarProduto(route.params.id, payload)
      mensagemSucesso.value = 'Produto atualizado com sucesso!'
    } else {
      await cadastrarProduto(payload)
      mensagemSucesso.value = 'Produto cadastrado com sucesso!'
      
      produto.value.nome = ''
      produto.value.descricao = ''
      produto.value.preco = ''
      produto.value.quantidade = ''
      produto.value.categoriaId = ''
    }

    setTimeout(() => {
      mensagemSucesso.value = ''
    }, 3000)

  } catch (e) {
    console.error(e)
    mensagemErro.value = 'Erro ao salvar. Verifique se todos campos foram preenchidos corretamente.'
  } finally {
    salvando.value = false
  }
}

onMounted(() => {
  carregarCategorias()
  carregarProduto()
})
</script>