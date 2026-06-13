import { api } from "./api";

export async function buscarProdutosPaginados({ page = 0, size = 10, nome, categoriaId } = {}) {
    const params = { page, size };
    
    if (nome && nome.trim()) {
        params.nome = nome.trim();
    }
    if (categoriaId) {
        params.categoriaId = categoriaId;
    }
    
    const response = await api.get('/produtos', { params });
    return response.data;
}

export async function buscarProdutoPorId(id) {
    const response = await api.get(`/produtos/${id}`);
    return response.data;
}

export async function cadastrarProduto(produto) {
    const response = await api.post('/produtos', produto);
    return response.data;
}

export async function atualizarProduto(id, produto) {
    const response = await api.put(`/produtos/${id}`, produto);
    return response.data;
}

export async function excluirProduto(id) {
    const response = await api.delete(`/produtos/${id}`);
    return response.data;
}