package com.danilo.financeiro.financialhealth.security;

import com.danilo.financeiro.financialhealth.domain.model.Usuario;
import com.danilo.financeiro.financialhealth.domain.service.UsuarioService;
import com.danilo.financeiro.financialhealth.dto.usuario.UsuarioResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper mapper;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain
    ) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        // Authorization = "1234654654sdfadsfds.sfadsfadsf123154" que seria o token Jwt

        if (header != null && header.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken auth = getAuthentication(
                    //TODO Extrair o magic number para uma constante
                    header.substring(7) //para desconsiderar o bearer e pegar somente o token
                    );

            if (auth != null && auth.isAuthenticated()){
                /**
                 * Passa a autenticação para o contexto do Spring tomar conta
                 */
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (jwtUtil.isValidToken(token)) {
            String usernameEmail = jwtUtil.getUserName(token);
            UsuarioResponseDto usuarioDto = usuarioService.obterPorEmail(usernameEmail);
            Usuario usuario = mapper.map(usuarioDto, Usuario.class);

            return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        }

        return null;
    }
}
