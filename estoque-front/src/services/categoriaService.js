import { api } from './api.js'

export async function listarCategorias() {
    const response = await api.get('/categorias/todas')
    return response.data
}

export async function buscarCategoriaPorId(id) {
    const response = await api.get(`/categorias/${id}`)
    return response.data
}

export async function cadastrarCategoria(categoria) {
    const response = await api.post('/categorias', categoria)
    return response.data
}

export async function atualizarCategoria(id, categoria) {
    const response = await api.put(`/categorias/${id}`, categoria)
    return response.data
}

export async function excluirCategoria(id) {
    await api.delete(`/categorias/${id}`)
}