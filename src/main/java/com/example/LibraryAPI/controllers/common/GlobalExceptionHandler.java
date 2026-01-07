package com.example.LibraryAPI.controllers.common;

import com.example.LibraryAPI.controllers.DTOs.ErroCampo;
import com.example.LibraryAPI.controllers.DTOs.ErroResponse;
import com.example.LibraryAPI.exceptions.OperacaoNaoPermitida;
import com.example.LibraryAPI.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrorList = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrorList
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect((Collectors.toList()));
        return new ErroResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validacao",
                listaErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResponse handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErroResponse.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitida.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResponse handleOperacaoNaoPermitidaException(OperacaoNaoPermitida e){
        return ErroResponse.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResponse handleErrosNaoTratados(RuntimeException e){
        return new ErroResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado no sistema",
                List.of());
    }
}
