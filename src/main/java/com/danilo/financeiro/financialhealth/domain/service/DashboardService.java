package com.danilo.financeiro.financialhealth.domain.service;

import com.danilo.financeiro.financialhealth.dto.dashboard.DashboardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private TituloService tituloService;

    public DashboardResponseDto obterFluxoDeCaixa(
            String periodoInicial, String periodoFinal
            ){

        //Aqui que acontece a Magia kkkkk
        return new DashboardResponseDto();
    }
}
