package br.unisinos.estoque.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.unisinos.estoque.dto.request.CategoriaRequestDTO;
import br.unisinos.estoque.dto.response.CategoriaResponseDTO;
import br.unisinos.estoque.entity.Categoria;
import br.unisinos.estoque.exception.RecursoNaoEncontradoException;
import br.unisinos.estoque.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaResponseDTO salvar(CategoriaRequestDTO categoriaDTO) {
        Categoria categoria = new Categoria();

        categoria.setNome(categoriaDTO.getNome());
        
        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return toResponseDTO(categoriaSalva);
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaAtualizadaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada com o ID: " + id));

        categoria.setNome(categoriaAtualizadaDTO.getNome());
        
        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return toResponseDTO(categoriaSalva);
    }

    public List<CategoriaResponseDTO> listaTodas() {
        List<Categoria> categorias = categoriaRepository.findAll();

        List<CategoriaResponseDTO> listaCategoriaDTO = new ArrayList<>();

        for (Categoria categoria : categorias) {
            CategoriaResponseDTO dto = toResponseDTO(categoria);
            listaCategoriaDTO.add(dto);
        }

        return listaCategoriaDTO;
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada com o ID: " + id));
        
        return toResponseDTO(categoria);
    }

    public void excluir(Long id) {
        categoriaRepository.deleteById(id);
    }

    // Response DTO padrão para todos os métodos necessários
    public CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        CategoriaResponseDTO categoriaDTO = new CategoriaResponseDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNome(categoria.getNome());

        return categoriaDTO;
    }
}
