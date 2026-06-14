<script setup>
import { onMounted, ref } from 'vue';
// Importa o serviço do SEU projeto
import { listarCategorias } from '../services/categoriaService';

const props = defineProps({
    filtros: {
        type: Object,
        required: true,
    }
})

// Adicionamos o evento 'buscar' que o seu projeto já usava
defineEmits(['limpar', 'buscar'])

const categorias = ref([])
const erroCategorias = ref('')

onMounted(async () => {
    try {
        const dados = await listarCategorias()
        categorias.value = Array.isArray(dados) ? dados : []
    } catch (e) {
        erroCategorias.value = 'Não foi possível carregar as categorias.'
        console.error(e)
    }
})
</script>

<template>
    <section class="filtros">
        <header class="filtros-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
            <h3>Filtros</h3>
            <div>
                <button type="button" @click="$emit('limpar')" style="margin-right: 10px;">Limpar</button>
                <button type="button" @click="$emit('buscar')">Buscar</button>
            </div>
        </header>

        <div class="filtros-grid" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem;">
            <label class="campo">
                <span style="display: block; margin-bottom: 0.5rem;">Buscar por nome</span>
                <input 
                    type="search" 
                    v-model="props.filtros.nome" 
                    @keyup.enter="$emit('buscar')" 
                    placeholder="Ex.: notebook"
                    style="width: 100%; padding: 0.5rem;"
                >
            </label>

            <label class="campo">
                <span style="display: block; margin-bottom: 0.5rem;">Categoria</span>
                <select v-model="props.filtros.categoriaId" @change="$emit('buscar')" style="width: 100%; padding: 0.5rem;">
                    <option :value="null">Todas</option>
                    <option 
                        v-for="categoria in categorias"
                        :key="categoria.id"
                        :value="categoria.id">
                        {{ categoria.nome }}
                    </option>
                </select>
                <small v-if="erroCategorias" style="color: red;">
                    {{ erroCategorias }}
                </small>
            </label>

            <label class="campo">
                <span style="display: block; margin-bottom: 0.5rem;">Preço mínimo</span>
                <input 
                    type="number" min="0" step="0.01" 
                    v-model.number="props.filtros.precoMin" 
                    @keyup.enter="$emit('buscar')" 
                    placeholder="0,00"
                    style="width: 100%; padding: 0.5rem;"
                >
            </label>

            <label class="campo">
                <span style="display: block; margin-bottom: 0.5rem;">Preço máximo</span>
                <input 
                    type="number" min="0" step="0.01" 
                    v-model.number="props.filtros.precoMax" 
                    @keyup.enter="$emit('buscar')" 
                    placeholder="0,00"
                    style="width: 100%; padding: 0.5rem;"
                >
            </label>
        </div>
    </section>
</template>