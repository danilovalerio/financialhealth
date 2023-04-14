package com.danilo.financeiro.financialhealth.domain.repository;

import com.danilo.financeiro.financialhealth.domain.model.CentroDeCusto;
import com.danilo.financeiro.financialhealth.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CentroDeCustoRepository extends JpaRepository<CentroDeCusto, Long> {

    List<CentroDeCusto> findByUsuario(Usuario usuario);

}
