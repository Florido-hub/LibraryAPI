package com.example.LibraryAPI.controllers.DTOs;

import com.example.LibraryAPI.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LivroDetailsDTO (
        UUID id,
        String isbn,
        String tittle,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        AutorDTO autor
){
}
