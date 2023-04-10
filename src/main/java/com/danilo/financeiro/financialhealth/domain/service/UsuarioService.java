package com.danilo.financeiro.financialhealth.domain.service;

import com.danilo.financeiro.financialhealth.domain.model.Usuario;
import com.danilo.financeiro.financialhealth.domain.respository.UsuarioRepository;
import com.danilo.financeiro.financialhealth.dto.usuario.UsuarioRequestDto;
import com.danilo.financeiro.financialhealth.dto.usuario.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioService implements ICRUDService<UsuarioRequestDto, UsuarioResponseDto> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<UsuarioResponseDto> obterTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        List<UsuarioResponseDto> usuariosDto = new ArrayList<>();

        /**for (Usuario usuario : usuarios){
            UsuarioResponseDto dto = mapper.map(usuario, UsuarioResponseDto.class);
            usuariosDto.add(dto);
        }
        return usuariosDto;*/

        // ou ------------------------------------------------>
        return usuarios.stream().map(
                usuario -> mapper.map(usuario, UsuarioResponseDto.class)
        ).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDto obterPorId(Long id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);

        if (optUsuario.isEmpty()) {
            //Lançar uma Exception
        }

        return mapper.map(optUsuario.get(), UsuarioResponseDto.class);
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
