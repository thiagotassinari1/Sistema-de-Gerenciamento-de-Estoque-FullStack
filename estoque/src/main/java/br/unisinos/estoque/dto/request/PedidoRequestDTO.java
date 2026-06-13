package br.unisinos.estoque.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class PedidoRequestDTO {
    
    @NotNull(message = "Informe uma quantidade válida.")
    @DecimalMin(value = "0.0", inclusive = false)
    private Integer quantidade;

    @NotNull(message = "O ID do produto é obritarório.")
    private Long produtoId;

    @NotNull(message = "O ID da categoria é obritarório.")
    private Long fornecedorId;
}
