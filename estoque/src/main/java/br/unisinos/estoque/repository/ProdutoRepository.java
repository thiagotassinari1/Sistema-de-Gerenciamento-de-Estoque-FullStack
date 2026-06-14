package br.unisinos.estoque.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

import br.unisinos.estoque.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

        @Query("""
                        select p
                        from Produto p
                        where (cast(:nome as String) is null or lower(p.nome) like lower(concat('%', cast(:nome as String), '%')))
                          and (cast(:categoriaId as Long) is null or p.categoria.id = :categoriaId)
                          and (cast(:precoMin as BigDecimal) is null or p.preco >= :precoMin)
                          and (cast(:precoMax as BigDecimal) is null or p.preco <= :precoMax)
                        """)
        Page<Produto> buscarComFiltros(
                        @Param("nome") String nome,
                        @Param("categoriaId") Long categoriaId,
                        @Param("precoMin") BigDecimal precoMin,
                        @Param("precoMax") BigDecimal precoMax,
                        Pageable pageable);
}