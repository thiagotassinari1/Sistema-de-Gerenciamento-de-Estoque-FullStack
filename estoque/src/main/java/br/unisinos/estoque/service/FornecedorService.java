package br.unisinos.estoque.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.unisinos.estoque.dto.request.FornecedorRequestDTO;
import br.unisinos.estoque.dto.response.FornecedorResponseDTO;
import br.unisinos.estoque.entity.Fornecedor;
import br.unisinos.estoque.exception.RecursoNaoEncontradoException;
import br.unisinos.estoque.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorResponseDTO salvar(FornecedorRequestDTO fornecedorDTO) {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setNome(fornecedorDTO.getNome());
        fornecedor.setCnpj(fornecedorDTO.getCnpj());

        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);

        return toResponseDTO(fornecedorSalvo);
    }

    public FornecedorResponseDTO atualizar(Long id, FornecedorRequestDTO fornecedorAtualizadoDTO) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado com o ID: " + id));

        fornecedor.setNome(fornecedorAtualizadoDTO.getNome());
        fornecedor.setCnpj(fornecedorAtualizadoDTO.getCnpj());
        
        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);

        return toResponseDTO(fornecedorSalvo);
    }

    public List<FornecedorResponseDTO> listaTodas() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();

        List<FornecedorResponseDTO> listaFornecedorDTO = new ArrayList<>();

        for (Fornecedor fornecedor : fornecedores) {
            FornecedorResponseDTO dto = toResponseDTO(fornecedor);
            listaFornecedorDTO.add(dto);
        }

        return listaFornecedorDTO;
    }

    public FornecedorResponseDTO buscarPorId(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado com o ID: " + id));
        
        return toResponseDTO(fornecedor);
    }

    public void excluir(Long id) {
        fornecedorRepository.deleteById(id);
    }

    // Response DTO padrão para todos os métodos necessários
    public FornecedorResponseDTO toResponseDTO(Fornecedor fornecedor) {
        FornecedorResponseDTO fornecedorDTO = new FornecedorResponseDTO();
        fornecedorDTO.setId(fornecedor.getId());
        fornecedorDTO.setNome(fornecedor.getNome());
        fornecedorDTO.setCnpj(fornecedor.getCnpj());

        return fornecedorDTO;
    }
}
