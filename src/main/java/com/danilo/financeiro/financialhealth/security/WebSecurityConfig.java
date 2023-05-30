package com.danilo.financeiro.financialhealth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Configuration - Por se tratar de um arquivo configurador
 * @EnableWebSecurity - componente de segurançca Web
 *
 * Não extende mais de nenhuma outra classe nas novas versoes
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private UserDetailsSecurityServer userDetailsSecurityServer;

    /**
     * Criptografador da senha
     * @return
     *
     * @Bean - para usar em qualquer lugar
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //Config inicial para autenticação do protocolo HTTP de Autenticacao
        httpSecurity
                .headers().frameOptions().disable().and()
                .cors().and().csrf().disable()
                .authorizeHttpRequests((auth) ->
                        //Toda vez que alguém fizer um post para essa URL permitimos todos
                        auth.requestMatchers(HttpMethod.POST, "/api/usuarios","/swagger-ui/index.html").permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/swagger-ui/**",
                                        "/swagger-resources/*",
                                        "/v3/api-docs/**"
                                ).permitAll()
                                //para qualquer outro request tem que estar autenticado
                                .anyRequest()
                                .authenticated())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //Configuracao do filtro para autenticacao e autorizacao
        httpSecurity.addFilter(new JwtAuthenticationFilter(
                authenticationManager(authenticationConfiguration), jwtUtil)
        );

        httpSecurity.addFilter(new JwtAuthorizationFilter(
                authenticationManager(authenticationConfiguration), jwtUtil, userDetailsSecurityServer)
        );

        return httpSecurity.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }
}
