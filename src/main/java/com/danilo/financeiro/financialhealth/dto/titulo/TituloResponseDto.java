package com.danilo.financeiro.financialhealth.dto.titulo;

import com.danilo.financeiro.financialhealth.domain.Enum.ETipoTitulo;
import com.danilo.financeiro.financialhealth.dto.centrodecusto.CentroDeCustoResponseDto;

import java.util.Date;
import java.util.List;

public class TituloResponseDto {
    private Long id;
    private String descricao;
    private ETipoTitulo tipo;
    private List<CentroDeCustoResponseDto> centrosDeCustos;
    private Double valor;
    private Date dataCadastro;
    private Date dataReferencia;
    private Date dataVencimento;
    private Date dataPagamento;
    private Date dataInativacao;
    private String observacao;

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

    public ETipoTitulo getTipo() {
        return tipo;
    }

    public void setTipo(ETipoTitulo tipo) {
        this.tipo = tipo;
    }

    public List<CentroDeCustoResponseDto> getCentrosDeCustos() {
        return centrosDeCustos;
    }

    public void setCentrosDeCustos(List<CentroDeCustoResponseDto> centrosDeCustos) {
        this.centrosDeCustos = centrosDeCustos;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataReferencia() {
        return dataReferencia;
    }

    public void setDataReferencia(Date dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(Date dataInativacao) {
        this.dataInativacao = dataInativacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
