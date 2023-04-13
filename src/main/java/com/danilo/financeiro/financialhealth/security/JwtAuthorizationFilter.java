package com.danilo.financeiro.financialhealth.security;

import com.danilo.financeiro.financialhealth.domain.model.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;

    private UserDetailsSecurityServer userDetailsSecurityServer;

    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserDetailsSecurityServer userDetailsSecurityServer) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsSecurityServer = userDetailsSecurityServer;
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

            Usuario usuario = (Usuario) userDetailsSecurityServer.loadUserByUsername(usernameEmail);

            return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        }

        return null;
    }
}
