package com.danilo.financeiro.financialhealth.domain.model;

import java.util.List;

public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String foto;
    private String dataCadastro;
    private String dataInativacao;
    private List<Titulo> titulos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(String dataInativacao) {
        this.dataInativacao = dataInativacao;
    }

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public void setTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
    }
}
