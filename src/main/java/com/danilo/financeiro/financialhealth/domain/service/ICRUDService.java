package com.danilo.financeiro.financialhealth.domain.service;

import java.util.List;

/**
 * Contrato para todos os CRUDS
 * @param <Req>
 * @param <Res>
 */
public interface ICRUDService <Req, Res>{

    List<Res> obterTodos();
    Res obterPorId(Long id);
    Res cadastrar(Req dto);
    Res atualizar(Long id, Req dto);
    void deletar(Long id);
}
