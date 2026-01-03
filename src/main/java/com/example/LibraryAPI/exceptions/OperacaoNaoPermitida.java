package com.example.LibraryAPI.exceptions;

public class OperacaoNaoPermitida extends RuntimeException{
    public OperacaoNaoPermitida(String message) {
        super(message);
    }
}
