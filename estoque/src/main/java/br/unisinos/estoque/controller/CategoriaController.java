package br.unisinos.estoque.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unisinos.estoque.dto.request.CategoriaRequestDTO;
import br.unisinos.estoque.dto.response.CategoriaResponseDTO;
import br.unisinos.estoque.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor

public class CategoriaController {
    
    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> salvar(@Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        CategoriaResponseDTO categoriaSalva = categoriaService.salvar(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        CategoriaResponseDTO categoriaAtualizada = categoriaService.atualizar(id, categoriaDTO);
        return ResponseEntity.ok(categoriaAtualizada);
    }

    @GetMapping("/todas")
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(categoriaService.listaTodas());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        CategoriaResponseDTO categoriaDTO = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(categoriaDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> excluir(@PathVariable Long id) {
        categoriaService.buscarPorId(id);
        categoriaService.excluir(id);

        return ResponseEntity.noContent().build();
    }    
    
}
