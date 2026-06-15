import { api } from './api.js'

export async function listarPedidos() {
    const response = await api.get('/pedidos/todos')
    return response.data
}

export async function buscarPedidoPorId(id) {
    const response = await api.get(`/pedidos/${id}`)
    return response.data
}

export async function cadastrarPedido(pedido) {
    const response = await api.post('/pedidos', pedido)
    return response.data
}

export async function excluirPedido(id) {
    await api.delete(`/pedidos/${id}`)
}
