package com.danilo.financeiro.financialhealth.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "centrodecusto")
public class CentroDeCusto {
    private static final String CENTRO_DE_CUSTO = "centrosDeCustos";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCentroDeCusto")
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    /**
     * Muitos centros de custo para um Usuario
     * E se relaciona atraves do idUsuario
     */
    @ManyToOne()
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToMany(mappedBy = CENTRO_DE_CUSTO)
    @JsonBackReference //quando busca centor de custo não buscar os titulos
    private List<Titulo> titulos;

    private Date dataInativacao;

    public Date getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(Date dataInativacao) {
        this.dataInativacao = dataInativacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public void setTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
    }
}
