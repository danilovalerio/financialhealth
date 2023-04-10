package com.danilo.financeiro.financialhealth.domain.service;

import com.danilo.financeiro.financialhealth.dto.usuario.UsuarioRequestDto;
import com.danilo.financeiro.financialhealth.dto.usuario.UsuarioResponseDto;

import java.util.List;

public class UsuarioService implements ICRUDService<UsuarioRequestDto, UsuarioResponseDto> {
    @Override
    public List<UsuarioResponseDto> obterTodos() {
        return null;
    }

    @Override
    public UsuarioResponseDto obterPorId(Long id) {
        return null;
    }

    @Override
    public UsuarioResponseDto cadastrar(UsuarioRequestDto dto) {
        return null;
    }

    @Override
    public UsuarioResponseDto atualizar(Long id, UsuarioRequestDto dto) {
        return null;
    }

    @Override
    public void deletar(Long id) {

    }
}
