import { api } from './api.js'

export async function listarFornecedores() {
    const response = await api.get('/fornecedores/todos')
    return response.data
}

export async function buscarFornecedorPorId(id) {
    const response = await api.get(`/fornecedores/${id}`)
    return response.data
}

export async function cadastrarFornecedor(fornecedor) {
    const response = await api.post('/fornecedores', fornecedor)
    return response.data
}

export async function atualizarFornecedor(id, fornecedor) {
    const response = await api.put(`/fornecedores/${id}`, fornecedor)
    return response.data
}

export async function excluirFornecedor(id) {
    await api.delete(`/fornecedores/${id}`)
}
