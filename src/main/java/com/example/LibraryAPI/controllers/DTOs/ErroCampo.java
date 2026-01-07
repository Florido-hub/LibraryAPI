package com.example.LibraryAPI.controllers.DTOs;

public record ErroCampo(
        String campo,
        String erro
) {
}
