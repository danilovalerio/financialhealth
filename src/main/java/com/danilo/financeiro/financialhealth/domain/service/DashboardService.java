package com.danilo.financeiro.financialhealth.domain.service;

import com.danilo.financeiro.financialhealth.domain.Enum.ETipoTitulo;
import com.danilo.financeiro.financialhealth.dto.dashboard.DashboardResponseDto;
import com.danilo.financeiro.financialhealth.dto.titulo.TituloResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private TituloService tituloService;

    public DashboardResponseDto obterFluxoDeCaixa(
            String periodoInicial, String periodoFinal
            ){

        List<TituloResponseDto> titulos =
                tituloService.obterPorDataDeVencimento(periodoInicial, periodoFinal);

        //Tudo que precisamos devolver para o response
        Double totalApagar = 0.0;
        Double totalAreceber = 0.0;
        Double saldo = 0.0;

        List<TituloResponseDto> titulosApagar = new ArrayList<>();
        List<TituloResponseDto> titulosAreceber = new ArrayList<>();

        for (TituloResponseDto titulo : titulos) {
            if (titulo.getTipo() == ETipoTitulo.APAGAR){
                totalApagar += titulo.getValor();
                titulosApagar.add(titulo);
            } else  {
                totalAreceber += titulo.getValor();
                titulosAreceber.add(titulo);
            }
        }

        saldo = totalAreceber - totalApagar;

        //Aqui que acontece a Magia kkkkk
        return new DashboardResponseDto(
                totalApagar, totalAreceber, saldo, titulosApagar, titulosAreceber
        );
    }
}
