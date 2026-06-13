package br.unisinos.estoque.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FornecedorRequestDTO {
    
    @NotBlank
    private String nome;

    @NotBlank
    @Size(min = 14, max = 14)
    private String cnpj;
}
