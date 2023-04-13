package com.danilo.financeiro.financialhealth.security;

import com.danilo.financeiro.financialhealth.domain.model.Usuario;
import com.danilo.financeiro.financialhealth.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * UserDetailsService - Sera usado pelo Spring para obter usuario pelo ID na hora de autentica
 */
@Component
public class UserDetailsSecurityServer implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameEmail) throws UsernameNotFoundException{
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(usernameEmail);

        if (optionalUsuario.isEmpty()){
            throw new UsernameNotFoundException("Usuário ou senha inválidos!");
        }

        return optionalUsuario.get();
    }
}
