package com.danilo.financeiro.financialhealth.domain.service;

import com.danilo.financeiro.financialhealth.dto.dashboard.DashboardResponseDto;
import com.danilo.financeiro.financialhealth.dto.titulo.TituloResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //Aqui que acontece a Magia kkkkk
        return new DashboardResponseDto();
    }
}
