package com.example.LibraryAPI.DTOs;

import com.example.LibraryAPI.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LivroRequestDTO(
        String isbn,
        String tittle,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        UUID id_autor
){
}
