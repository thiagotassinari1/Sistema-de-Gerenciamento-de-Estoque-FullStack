package br.unisinos.estoque.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.unisinos.estoque.dto.request.PedidoRequestDTO;
import br.unisinos.estoque.dto.response.PedidoResponseDTO;
import br.unisinos.estoque.entity.Fornecedor;
import br.unisinos.estoque.entity.Pedido;
import br.unisinos.estoque.entity.Produto;
import br.unisinos.estoque.exception.RecursoNaoEncontradoException;
import br.unisinos.estoque.exception.RegraNegocioException;
import br.unisinos.estoque.repository.FornecedorRepository;
import br.unisinos.estoque.repository.PedidoRepository;
import br.unisinos.estoque.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;

    @Transactional
    public PedidoResponseDTO salvar(PedidoRequestDTO pedidoDTO) {

        Produto produto = produtoRepository.findById(pedidoDTO.getProdutoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto não encontrado com o ID: " + pedidoDTO.getProdutoId()));

        Fornecedor fornecedor = fornecedorRepository.findById(pedidoDTO.getFornecedorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Fornecedor não encontrado com o ID: " + pedidoDTO.getFornecedorId()));

        // Atualizar o estoque no produto
        int estoqueAtual = produto.getEstoque();
        produto.setEstoque(estoqueAtual + pedidoDTO.getQuantidade());
        produtoRepository.save(produto);

        Pedido pedido = new Pedido();
        pedido.setQuantidade(pedidoDTO.getQuantidade());
        pedido.setProduto(produto);
        pedido.setFornecedor(fornecedor);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return toResponseDTO(pedidoSalvo);
    }

    public List<PedidoResponseDTO> listarTodos() {

        List<Pedido> pedidos = pedidoRepository.findAll();

        List<PedidoResponseDTO> listaPedidosDTO = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            PedidoResponseDTO dto = toResponseDTO(pedido);
            listaPedidosDTO.add(dto);
        }

        return listaPedidosDTO;
    }

    public PedidoResponseDTO buscarPorId(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado com o ID: " + id));

        return toResponseDTO(pedido);
    }

    @Transactional
    public void cancelar(Long id) {
        
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado com o ID: " + id));


        // Conferir se o pedido já não foi cancelado
        if ("Cancelado".equals(pedido.getStatus())) {
            throw new RegraNegocioException("Pedido já cancelado.");
        }

        Produto produto = pedido.getProduto();

        int novoEstoque = produto.getEstoque() - pedido.getQuantidade();

        if (novoEstoque < 0) {
            throw new RegraNegocioException("Não é possível cancelar. O produto não possui estoque suficiente para o estorno.");
        }

        produto.setEstoque(novoEstoque);
        produtoRepository.save(produto);
        pedido.setStatus("Cancelado");
        pedidoRepository.save(pedido);
    }

    public PedidoResponseDTO toResponseDTO(Pedido pedido) {
        PedidoResponseDTO pedidoDTO = new PedidoResponseDTO();

        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setData(pedido.getData());
        pedidoDTO.setQuantidade(pedido.getQuantidade());
        pedidoDTO.setStatus(pedido.getStatus());

        pedidoDTO.setProdutoId(pedido.getProduto().getId());
        pedidoDTO.setNomeProduto(pedido.getProduto().getNome());

        pedidoDTO.setFornecedorId(pedido.getFornecedor().getId());
        pedidoDTO.setNomeFornecedor(pedido.getFornecedor().getNome());

        return pedidoDTO;
    }

}
