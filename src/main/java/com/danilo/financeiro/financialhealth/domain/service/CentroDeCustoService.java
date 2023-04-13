package com.danilo.financeiro.financialhealth.domain.service;

import com.danilo.financeiro.financialhealth.dto.centrodecusto.CentroDeCustoRequestDto;
import com.danilo.financeiro.financialhealth.dto.centrodecusto.CentroDeCustoResponseDto;

import java.util.List;

public class CentroDeCustoService implements ICRUDService<CentroDeCustoRequestDto, CentroDeCustoResponseDto>{
    @Override
    public List<CentroDeCustoResponseDto> obterTodos() {
        return null;
    }

    @Override
    public CentroDeCustoResponseDto obterPorId(Long id) {
        return null;
    }

    @Override
    public CentroDeCustoResponseDto cadastrar(CentroDeCustoRequestDto dto) {
        return null;
    }

    @Override
    public CentroDeCustoResponseDto atualizar(Long id, CentroDeCustoRequestDto dto) {
        return null;
    }

    @Override
    public void deletar(Long id) {

    }
}
