package com.danilo.financeiro.financialhealth.domain.repository;

import com.danilo.financeiro.financialhealth.domain.model.Titulo;
import com.danilo.financeiro.financialhealth.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TituloRepository extends JpaRepository<Titulo, Long> {

    /**
     * @Query Executa uma query nativa e ou do Spring
     * @return
     *
     * @Param - posso usar na query SQL
     */
    @Query(nativeQuery = true,
            value = "SELECT * FROM public.titulo " +
            "WHERE data_vencimento BETWEEN TO_TIMESTAMP(:periodoInicial,'YYYY-MM-DD hh24:MI:SS') AND "+
            "TO_TIMESTAMP(:periodoFinal,'YYYY-MM-DD hh24:MI:SS')")
    List<Titulo> obterFluxoCaixaPorDataVencimento(
            @Param("periodoInicial") String periodoInicial,
            @Param("periodoFinal") String periodoFinal
    );

    List<Titulo> findByUsuario(Usuario usuario);
}
