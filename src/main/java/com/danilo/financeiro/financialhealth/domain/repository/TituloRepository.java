package com.danilo.financeiro.financialhealth.domain.repository;

import com.danilo.financeiro.financialhealth.domain.model.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TituloRepository extends JpaRepository<Titulo, Long> {
}
