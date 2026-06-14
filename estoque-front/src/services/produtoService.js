import { api } from "./api";

export const buscarProdutosPaginados = async (filtros) => {
    // Começamos apenas com a paginação
    const params = {
        page: filtros.page,
        size: filtros.size
    };

    // Só adicionamos os filtros se eles não forem nulos ou vazios
    if (filtros.nome) params.nome = filtros.nome;
    if (filtros.categoriaId) params.categoriaId = filtros.categoriaId;
    if (filtros.precoMin) params.precoMin = filtros.precoMin;
    if (filtros.precoMax) params.precoMax = filtros.precoMax;

    const response = await api.get('/produtos', { params });
    return response.data;
};

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