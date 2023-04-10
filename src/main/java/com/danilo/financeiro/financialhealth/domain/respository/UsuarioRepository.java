package com.danilo.financeiro.financialhealth.domain.respository;

import com.danilo.financeiro.financialhealth.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Para autenticação
    List<Usuario> findByEmail(String email);
}
