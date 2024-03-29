package com.danilo.financeiro.financialhealth.security;

import com.danilo.financeiro.financialhealth.common.ConversorData;
import com.danilo.financeiro.financialhealth.domain.model.ErrorResposta;
import com.danilo.financeiro.financialhealth.domain.model.Usuario;
import com.danilo.financeiro.financialhealth.dto.usuario.LoginRequestDto;
import com.danilo.financeiro.financialhealth.dto.usuario.LoginResponseDto;
import com.danilo.financeiro.financialhealth.dto.usuario.UsuarioResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

/**
 * Autenticacao para acessar a API
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

        /**
         * Só vai chegar no filtro de autenticação quando for uma URL Específica
         * Assim que bater na URL chega nesse filter
         */
        setFilterProcessesUrl("/api/auth");
    }

    /**
     * Se aqui der OK e está tudo correto, o Spring chama o "successfulAuthentication"
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            //Pegamos o request e tentando transformar no Request
            LoginRequestDto login = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            /**
             * Aqui o login do Spring valida se o email e senha sao validos
             */
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    login.getEmail(),
                    login.getSenha()
            );
            /**
             * Aqui faz a autenticação
             */
            Authentication auth = authenticationManager.authenticate(authToken);
            return auth;

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Usuário ou senha inválidos " + e);
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    /**
     * Toda autenticação que estiver ok deu certo a autenticacao
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authResult) throws IOException {

        //faz o casting para usuario do objeto getPrincipal
        Usuario usuario = (Usuario) authResult.getPrincipal();

        //valida se usuario está ativo
        if (usuario.getDataInativacao() != null) {
            ErrorResposta erro = new ErrorResposta(
                    ConversorData.converterDateParaDataEHora(new Date()),
                    HttpStatus.UNAUTHORIZED.value(),
                    "Unauthorized",
                    "Verifique as informações e tente novamente."
            );

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(erro));
            return;
        }

        //gera um token para o Usuario
        String token = jwtUtil.gerarToken(authResult);

        /* Trazer os dados e montar propriedade por propriedade */

        UsuarioResponseDto usuarioResponse = new UsuarioResponseDto();
        usuarioResponse.setId(usuario.getId());
        usuarioResponse.setEmail(usuario.getEmail());
        usuarioResponse.setNome(usuario.getNome());
        usuarioResponse.setFoto(usuario.getFoto());
        usuarioResponse.setDataInativacao(usuario.getDataInativacao());
        usuarioResponse.setDataCadastro(usuario.getDataCadastro());

        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setToken("Bearer " + token);
        loginResponse.setUsuario(usuarioResponse);

        /**
         * Quando for adicionar resposta no BODY
         * configura o tipo de resposta com encoding e tipo de retorno
         */
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        response.getWriter().write(new Gson().toJson(loginResponse));
    }

    /**
     * Se der um erro esse é o método chamada pelo Spring
     */
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException {

        String dataHora = ConversorData.converterDateParaDataEHora(new Date());

        ErrorResposta erro = new ErrorResposta(
                dataHora, HttpStatus.UNAUTHORIZED.value(), "Unauthorized", failed.getMessage()
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(erro));

    }
}
