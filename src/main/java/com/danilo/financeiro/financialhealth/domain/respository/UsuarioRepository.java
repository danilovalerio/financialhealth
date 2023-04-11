package com.danilo.financeiro.financialhealth.domain.respository;

import com.danilo.financeiro.financialhealth.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Para autenticação
    Optional<Usuario> findByEmail(String email);
}
