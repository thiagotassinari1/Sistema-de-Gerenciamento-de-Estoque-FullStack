package br.unisinos.estoque.service;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.unisinos.estoque.dto.request.ProdutoRequestDTO;
import br.unisinos.estoque.dto.response.ProdutoResponseDTO;
import br.unisinos.estoque.entity.Categoria;
import br.unisinos.estoque.entity.Produto;
import br.unisinos.estoque.exception.RecursoNaoEncontradoException;
import br.unisinos.estoque.exception.RegraNegocioException;
import br.unisinos.estoque.repository.CategoriaRepository;
import br.unisinos.estoque.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoResponseDTO salvar(ProdutoRequestDTO produtoDTO) {
        Categoria categoria = categoriaRepository.findById(produtoDTO.getCategoriaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria não encontrada com id: " + produtoDTO.getCategoriaId()));

        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setEstoque(produtoDTO.getEstoque());
        produto.setCategoria(categoria);

        Produto produtoSalvo = produtoRepository.save(produto);
        return toResponseDTO(produtoSalvo);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produtoAtualizadoDTO) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com o id: " + id));

        Categoria categoria = categoriaRepository.findById(produtoAtualizadoDTO.getCategoriaId())
                .orElseThrow(() -> new RegraNegocioException(
                        "Categoria não encontrada com id: " + produtoAtualizadoDTO.getCategoriaId()));

        produto.setNome(produtoAtualizadoDTO.getNome());
        produto.setDescricao(produtoAtualizadoDTO.getDescricao());
        produto.setPreco(produtoAtualizadoDTO.getPreco());
        produto.setEstoque(produtoAtualizadoDTO.getEstoque());
        produto.setCategoria(categoria);

        Produto produtoSalvo = produtoRepository.save(produto);
        return toResponseDTO(produtoSalvo);
    }

    public List<ProdutoResponseDTO> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoResponseDTO> listaProdutosDTO = new ArrayList<>();
        for (Produto produto : produtos) {
            listaProdutosDTO.add(toResponseDTO(produto));
        }
        return listaProdutosDTO;
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com o id: " + id));
        return toResponseDTO(produto);
    }

    public void excluir(Long id) {
        produtoRepository.deleteById(id);
    }

    public Page<ProdutoResponseDTO> buscar(String nome, Long categoriaId, BigDecimal precoMin, BigDecimal precoMax, Pageable pageable) {
        return produtoRepository.buscarComFiltros(nome, categoriaId, precoMin, precoMax, pageable).map(this::toResponseDTO);
    }

    public ProdutoResponseDTO toResponseDTO(Produto produto) {
        ProdutoResponseDTO produtoDTO = new ProdutoResponseDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setDescricao(produto.getDescricao());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setEstoque(produto.getEstoque());

        if (produto.getCategoria() != null) {
            produtoDTO.setCategoriaId(produto.getCategoria().getId());
            produtoDTO.setNomeCategoria(produto.getCategoria().getNome());
        }
        return produtoDTO;
    }
}