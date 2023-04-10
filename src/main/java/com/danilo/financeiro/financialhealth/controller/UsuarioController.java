package com.danilo.financeiro.financialhealth.controller;

import com.danilo.financeiro.financialhealth.domain.service.UsuarioService;
import com.danilo.financeiro.financialhealth.dto.usuario.UsuarioRequestDto;
import com.danilo.financeiro.financialhealth.dto.usuario.UsuarioResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*") //aceita origin de qualquer lugar, desde que esteja autenticada
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> obterTodos(){
        return ResponseEntity.ok(usuarioService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> obterPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(usuarioService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> cadastrar(
            @RequestBody UsuarioRequestDto dto
    ){
        UsuarioResponseDto usuario = usuarioService.cadastrar(dto);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDto dto
    ){
        UsuarioResponseDto usuario = usuarioService.atualizar(id, dto);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        usuarioService.deletar(id);
        //NO_CONTENT - Usamos quando fizemos uma ação e não tem o que retornar, como o caso do deletar
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
