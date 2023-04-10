package com.danilo.financeiro.financialhealth.domain.exception;

public class ResourceBadRequestException extends RuntimeException {

    public ResourceBadRequestException(String mensagem){
        super(mensagem);
    }

}
