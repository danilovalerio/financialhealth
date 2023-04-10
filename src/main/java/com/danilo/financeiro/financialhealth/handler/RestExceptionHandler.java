package com.danilo.financeiro.financialhealth.handler;

import com.danilo.financeiro.financialhealth.common.ConversorData;
import com.danilo.financeiro.financialhealth.domain.exception.ResourceBadRequestException;
import com.danilo.financeiro.financialhealth.domain.exception.ResourceNotFoundException;
import com.danilo.financeiro.financialhealth.domain.model.ErrorResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

/**
 * Manipulador de exceções Rest, exceto Autenticação
 */

@ControllerAdvice
public class RestExceptionHandler {
    /**
     * Quando acontecer uma excessão desse tipo trata aqui
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResposta> handlerResourceNotFoundException(ResourceNotFoundException exception) {

        String dataHora = ConversorData.converterDateParaDataEHora(new Date());

        ErrorResposta erro = new ErrorResposta(
                dataHora, HttpStatus.NOT_FOUND.value(), "Not Found", exception.getMessage()
        );

        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<ErrorResposta> handlerResourceBadRequestException(ResourceNotFoundException exception) {

        String dataHora = ConversorData.converterDateParaDataEHora(new Date());

        ErrorResposta erro = new ErrorResposta(
                dataHora, HttpStatus.BAD_REQUEST.value(), "Bad Request", exception.getMessage()
        );

        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResposta> handlerRequestdException(Exception exception) {

        String dataHora = ConversorData.converterDateParaDataEHora(new Date());

        ErrorResposta erro = new ErrorResposta(
                dataHora, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", exception.getMessage()
        );

        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);


    }
}
