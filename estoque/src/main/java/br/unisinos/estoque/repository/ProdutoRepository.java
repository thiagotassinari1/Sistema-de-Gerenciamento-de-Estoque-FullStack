package br.unisinos.estoque.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.unisinos.estoque.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("""
                select p
                from Produto p
                where (:nome is null or lower(p.nome) like lower(concat('%', cast(:nome as String), '%')))
                    and (:categoriaId is null or p.categoria.id = :categoriaId)
            """)
    Page<Produto> buscarComFiltros(
            @Param("nome") String nome,
            @Param("categoriaId") Long categoriaId,
            Pageable pageable);
}