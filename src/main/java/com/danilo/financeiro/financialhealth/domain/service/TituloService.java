package com.danilo.financeiro.financialhealth.domain.service;

import com.danilo.financeiro.financialhealth.domain.exception.ResourceBadRequestException;
import com.danilo.financeiro.financialhealth.domain.exception.ResourceNotFoundException;
import com.danilo.financeiro.financialhealth.domain.model.Titulo;
import com.danilo.financeiro.financialhealth.domain.model.Usuario;
import com.danilo.financeiro.financialhealth.domain.repository.TituloRepository;
import com.danilo.financeiro.financialhealth.dto.titulo.TituloRequestDto;
import com.danilo.financeiro.financialhealth.dto.titulo.TituloResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TituloService implements ICRUDService<TituloRequestDto, TituloResponseDto> {

    @Autowired
    TituloRepository tituloRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<TituloResponseDto> obterTodos() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        List<Titulo> titulos = tituloRepository.findAll();
        List<Titulo> titulos = tituloRepository.findByUsuario(usuario);
        List<TituloResponseDto> titulosResponse = titulos.stream().map(
                titulo -> mapper.map(titulo, TituloResponseDto.class)
        ).collect(Collectors.toList());
        return titulosResponse;
    }

    @Override
    public TituloResponseDto obterPorId(Long id) {
        Optional<Titulo> optionalTitulo = tituloRepository.findById(id);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (optionalTitulo.isEmpty() || optionalTitulo.get().getUsuario().getId() != usuario.getId()) {
            throw new ResourceNotFoundException("Não foi encontrado o titulo com esse id "+id);
        }

        return mapper.map(optionalTitulo, TituloResponseDto.class);
    }

    @Override
    public TituloResponseDto cadastrar(TituloRequestDto dto) {
        validarTitulo(dto);
        /**
         * Pega o Request e convert num centro de custo model
         */
        Titulo tituloNovo = mapper.map(dto, Titulo.class);

        /**
         * Pegar o usuário do context de segurança da autenticação do Spring
         */
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        tituloNovo.setUsuario(usuario);
        tituloNovo.setId(null);
        tituloNovo = tituloRepository.save(tituloNovo);

        return mapper.map(tituloNovo, TituloResponseDto.class);
    }

    @Override
    public TituloResponseDto atualizar(Long id, TituloRequestDto dto) {
        obterPorId(id);
        validarTitulo(dto);
        Titulo tituloNovo = mapper.map(dto, Titulo.class);

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tituloNovo.setUsuario(usuario);

        tituloNovo.setId(id);
        tituloNovo = tituloRepository.save(tituloNovo);

        return mapper.map(tituloNovo, TituloResponseDto.class);
    }

    @Override
    public void deletar(Long id) {
        Optional<Titulo> optTitulo = tituloRepository.findById(id);

        if (optTitulo.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o titulo com o id: "+id);
        }

        Titulo titulo = optTitulo.get();
        titulo.setDataInativacao(new Date());

        tituloRepository.save(titulo);
    }

    public List<TituloResponseDto> obterPorDataDeVencimento(
            String periodoInicial, String periodoFinal
    ){
        List<Titulo> titulos = tituloRepository.obterFluxoCaixaPorDataVencimento(
                periodoInicial, periodoFinal
        );

        return titulos.stream()
                .map(titulo -> mapper.map(titulo, TituloResponseDto.class))
                .collect(Collectors.toList());

    }

    private void validarTitulo(TituloRequestDto dto) {
        if (dto.getTipo() == null ||
        dto.getDataVencimento() == null ||
        dto.getValor() == null ||
        dto.getDescricao() == null){
            throw new ResourceBadRequestException(
                    "Os campos Tipo, Data de Vencimento, Valor e Descrição são obrigatórios."
            );
        }
    }
}
