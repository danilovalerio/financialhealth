package com.danilo.financeiro.financialhealth.domain.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensagem){
        super(mensagem);
    }

}
