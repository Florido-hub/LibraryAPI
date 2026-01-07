package com.example.LibraryAPI.controllers.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "Nome obrigatorio")
        @Size(min = 2, max = 100, message = "campo fora do tamanho padrao")
        String nome,
        @NotNull(message = "campo obrigatorio")
        @Past(message = "data invalida")
        LocalDate dataNascimento,
        @NotBlank(message = "campo obrigatorio")
        @Size(min = 2, max = 50, message = "campo fora do tamanho padrao")
        String nacionalidade
) {
}
