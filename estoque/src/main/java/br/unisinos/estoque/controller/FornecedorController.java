package br.unisinos.estoque.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unisinos.estoque.dto.request.FornecedorRequestDTO;
import br.unisinos.estoque.dto.response.FornecedorResponseDTO;
import br.unisinos.estoque.service.FornecedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/fornecedores")
@RequiredArgsConstructor

public class FornecedorController {
    
    private final FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> salvar(@Valid @RequestBody FornecedorRequestDTO fornecedorDTO) {
        FornecedorResponseDTO fornecedorSalvo = fornecedorService.salvar(fornecedorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FornecedorRequestDTO fornecedorDTO) {
        FornecedorResponseDTO fornecedorAtualizado = fornecedorService.atualizar(id, fornecedorDTO);
        return ResponseEntity.ok(fornecedorAtualizado);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<FornecedorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(fornecedorService.listaTodas());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<FornecedorResponseDTO> buscarPorId(@PathVariable Long id) {
        FornecedorResponseDTO fornecedorDTO = fornecedorService.buscarPorId(id);
        return ResponseEntity.ok(fornecedorDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> excluir(@PathVariable Long id) {
        fornecedorService.buscarPorId(id);
        fornecedorService.excluir(id);

        return ResponseEntity.noContent().build();
    }    
    
}
