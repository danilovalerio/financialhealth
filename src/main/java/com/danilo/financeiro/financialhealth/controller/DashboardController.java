package com.danilo.financeiro.financialhealth.controller;

import com.danilo.financeiro.financialhealth.domain.service.DashboardService;
import com.danilo.financeiro.financialhealth.dto.dashboard.DashboardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponseDto> obterFluxoDeCaixa(
            @RequestParam(name = "periodoInicial") String periodoInicial,
            @RequestParam(name = "periodoFinal") String periodoFinal
    ){
        return ResponseEntity.ok(dashboardService.obterFluxoDeCaixa(periodoInicial, periodoFinal));
    }

}
