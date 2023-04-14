package com.danilo.financeiro.financialhealth.controller;

import com.danilo.financeiro.financialhealth.domain.service.CentroDeCustoService;
import com.danilo.financeiro.financialhealth.dto.centrodecusto.CentroDeCustoRequestDto;
import com.danilo.financeiro.financialhealth.dto.centrodecusto.CentroDeCustoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/centrosdecusto")
public class CentroDeCustoController {

    @Autowired
    private CentroDeCustoService centroDeCustoService;

    @GetMapping
    public ResponseEntity<List<CentroDeCustoResponseDto>> obterTodos(){
        return ResponseEntity.ok(centroDeCustoService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentroDeCustoResponseDto> obterPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(centroDeCustoService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<CentroDeCustoResponseDto> cadastrar(
            @RequestBody CentroDeCustoRequestDto dto
    ){
        CentroDeCustoResponseDto centroDeCusto = centroDeCustoService.cadastrar(dto);
        return new ResponseEntity<>(centroDeCusto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentroDeCustoResponseDto> atualizar(
            @PathVariable Long id, @RequestBody CentroDeCustoRequestDto dto
    ){
        return ResponseEntity.ok(centroDeCustoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        centroDeCustoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
