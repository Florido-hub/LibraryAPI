package com.example.LibraryAPI.controllers.DTOs;

import com.example.LibraryAPI.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LivroRequestDTO(

        @ISBN
        @NotBlank(message = "campo obrigatorio")
        String isbn,

        @NotBlank(message = "campo obrigatorio")
        String tittle,

        @NotNull (message = "campo obrigatorio")
        @Past(message = "Data invalida")
        LocalDate dataPublicacao,

        GeneroLivro genero,

        BigDecimal preco,

        @NotNull(message = "campo obrigatorio")
        UUID idAutor
){
}
