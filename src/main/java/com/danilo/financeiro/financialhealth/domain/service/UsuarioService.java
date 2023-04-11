package com.danilo.financeiro.financialhealth.domain.service;

import com.danilo.financeiro.financialhealth.domain.exception.ResourceBadRequestException;
import com.danilo.financeiro.financialhealth.domain.exception.ResourceNotFoundException;
import com.danilo.financeiro.financialhealth.domain.model.Usuario;
import com.danilo.financeiro.financialhealth.domain.respository.UsuarioRepository;
import com.danilo.financeiro.financialhealth.dto.usuario.UsuarioRequestDto;
import com.danilo.financeiro.financialhealth.dto.usuario.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
            throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id: "+ id);
        }

        return mapper.map(optUsuario.get(), UsuarioResponseDto.class);
    }

    @Override
    public UsuarioResponseDto cadastrar(UsuarioRequestDto dto) {
        validarUsuario(dto);

        Usuario usuario = mapper.map(dto, Usuario.class);
        usuario.setId(null);
        usuario.setDataCadastro(new Date());
        //TODO: criptografar a senha do usuário
        usuario = usuarioRepository.save(usuario);

        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    @Override
    public UsuarioResponseDto atualizar(Long id, UsuarioRequestDto dto) {
        UsuarioResponseDto usuarioBanco = obterPorId(id);
        validarUsuario(dto);

        Usuario usuario = mapper.map(dto, Usuario.class);
        //TODO: criptografar a senha do usuário
        usuario.setId(id);
        usuario.setDataInativacao(usuarioBanco.getDataInativacao());
        usuario = usuarioRepository.save(usuario);

        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    @Override
    public void deletar(Long id) {

        UsuarioResponseDto usuarioEncontrado = obterPorId(id);

        Usuario usuario = mapper.map(usuarioEncontrado, Usuario.class);

        usuario.setDataInativacao(new Date());

        usuarioRepository.save(usuario);
        //usuarioRepository.deleteById(id);

    }

    private void validarUsuario(UsuarioRequestDto dto){
        if (dto.getEmail() == null || dto.getSenha() == null || dto.getEmail().isEmpty() || dto.getSenha().isEmpty()){
            throw new ResourceBadRequestException("E-mail e ou senha são obrigatórios");
        }
    }
}
