package com.danilo.financeiro.financialhealth.dto.dashboard;

import com.danilo.financeiro.financialhealth.dto.titulo.TituloResponseDto;

import java.util.List;

public class DashBoardResponseDto {

    private Double totalApagar;
    private Double totalAreceber;
    private Double saldo;


    List<TituloResponseDto> titulosApagar;
    List<TituloResponseDto> titulosAreceber;

    DashBoardResponseDto(){

    }

    public DashBoardResponseDto(Double totalApagar, Double totalAreceber, Double saldo, List<TituloResponseDto> titulosApagar, List<TituloResponseDto> titulosAreceber) {
        this.totalApagar = totalApagar;
        this.totalAreceber = totalAreceber;
        this.saldo = saldo;
        this.titulosApagar = titulosApagar;
        this.titulosAreceber = titulosAreceber;
    }

    public Double getTotalApagar() {
        return totalApagar;
    }

    public void setTotalApagar(Double totalApagar) {
        this.totalApagar = totalApagar;
    }

    public Double getTotalAreceber() {
        return totalAreceber;
    }

    public void setTotalAreceber(Double totalAreceber) {
        this.totalAreceber = totalAreceber;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public List<TituloResponseDto> getTitulosApagar() {
        return titulosApagar;
    }

    public void setTitulosApagar(List<TituloResponseDto> titulosApagar) {
        this.titulosApagar = titulosApagar;
    }

    public List<TituloResponseDto> getTitulosAreceber() {
        return titulosAreceber;
    }

    public void setTitulosAreceber(List<TituloResponseDto> titulosAreceber) {
        this.titulosAreceber = titulosAreceber;
    }
}
