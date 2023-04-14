package com.danilo.financeiro.financialhealth.domain.service;

import com.danilo.financeiro.financialhealth.domain.exception.ResourceNotFoundException;
import com.danilo.financeiro.financialhealth.domain.model.CentroDeCusto;
import com.danilo.financeiro.financialhealth.domain.model.Usuario;
import com.danilo.financeiro.financialhealth.domain.repository.CentroDeCustoRepository;
import com.danilo.financeiro.financialhealth.dto.centrodecusto.CentroDeCustoRequestDto;
import com.danilo.financeiro.financialhealth.dto.centrodecusto.CentroDeCustoResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CentroDeCustoService implements ICRUDService<CentroDeCustoRequestDto, CentroDeCustoResponseDto>{

    @Autowired
    private CentroDeCustoRepository centroDeCustoRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<CentroDeCustoResponseDto> obterTodos() {
        List<CentroDeCusto> centrosDeCusto = centroDeCustoRepository.findAll();

        return centrosDeCusto.stream()
                .map(centroDeCusto -> mapper.map(centroDeCusto, CentroDeCustoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CentroDeCustoResponseDto obterPorId(Long id) {
        Optional<CentroDeCusto> optCentroDeCusto = centroDeCustoRepository.findById(id);

        if (optCentroDeCusto.isEmpty()) {
            throw new ResourceNotFoundException("Não foi encontrado o centro de custo com esse id "+id);
        }

        return mapper.map(optCentroDeCusto, CentroDeCustoResponseDto.class);
    }

    @Override
    public CentroDeCustoResponseDto cadastrar(CentroDeCustoRequestDto dto) {
        /**
         * Pega o Request e convert num centro de custo model
         */
        CentroDeCusto centroDeCustoNovo = mapper.map(dto, CentroDeCusto.class);

        /**
         * Pegar o usuário do context de segurança da autenticação do Spring
         */
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        centroDeCustoNovo.setUsuario(usuario);
        centroDeCustoNovo.setId(null);
        centroDeCustoNovo = centroDeCustoRepository.save(centroDeCustoNovo);

        return mapper.map(centroDeCustoNovo, CentroDeCustoResponseDto.class);
    }

    @Override
    public CentroDeCustoResponseDto atualizar(Long id, CentroDeCustoRequestDto dto) {

        obterPorId(id);
        CentroDeCusto centroDeCustoNovo = mapper.map(dto, CentroDeCusto.class);

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        centroDeCustoNovo.setUsuario(usuario);

        centroDeCustoNovo.setId(id);
        centroDeCustoNovo = centroDeCustoRepository.save(centroDeCustoNovo);

        return mapper.map(centroDeCustoNovo, CentroDeCustoResponseDto.class);
    }

    @Override
    public void deletar(Long id) {
        // Já valida se o cara existe
        obterPorId(id);

        centroDeCustoRepository.deleteById(id);

    }
}
