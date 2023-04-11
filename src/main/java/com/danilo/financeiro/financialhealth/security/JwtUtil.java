package com.danilo.financeiro.financialhealth.security;

import com.danilo.financeiro.financialhealth.domain.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * @Component  se torna um bean que pode ser importada em qualquer lugar
 */

@Component
public class JwtUtil {
    /**
     * Chaves que não deixamos no nosso código, normalmente fica em configs
     */

    @Value("${auth.jwt.secret}")
    private String jwtSecret;

    @Value("${auth.jwt-expiration-millisec}")
    private Long jwtExpirationMillisec;

    public String gerarToken(Authentication authentication) {
        /**
         * Data de Expiração do Token é de 1 dia, por isso time atual mais o valor em millisec
         */
        Date dataExpiracao = new Date(new Date().getTime() + jwtExpirationMillisec);

        /**
         * Aqui pegamos o usuário atual da autenticação
         */
        Usuario usuario = (Usuario) authentication.getPrincipal();

        try {

            /**
             * Gera uma chave em bytes com base na jwtSecret
             */
            Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));

            /**
             * Gera o token com o JWT
             */
            return Jwts.builder()
                    .setSubject(usuario.getUsername()) //dono do token representado por um dado unico no banco
                    .setIssuedAt(new Date()) //data que está gerando o token, OPCIONAL
                    .setExpiration(dataExpiracao) //data de expiracao
                    .signWith(secretKey)
                    .compact(); //compacta e gera uma string em formato de token

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    /**
     * Reivindicações - Claims a partir de um token com sua chave privada pega as infos dentro do mesmo
     * @param token que será desimcriptografado
     * @return
     */
    private Claims getClaims(String token) {
        try {
            Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));
            /**
             * Vai desimcriptografar o token e pegar o dados dentro dele
             */
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Pega email do usuario do token, por e-mail ser unico é o identificador
     * @param token
     * @return
     */

    public String getUserName(String token) {
        Claims claims = getClaims(token);

        if (claims == null) {
            return null;
        }

        return claims.getSubject();
    }

    public boolean isValidToken(String token) {
        Claims claims = getClaims(token);

        if (claims == null) {
            return false;
        }

        String email = claims.getSubject();
        Date dataExpiracao = claims.getExpiration();
        Date agora = new Date(System.currentTimeMillis());

        if (email != null && agora.before(dataExpiracao)){
            return true;
        }

        return false;
    }
}
