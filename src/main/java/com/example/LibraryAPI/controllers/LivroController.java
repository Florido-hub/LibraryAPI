package com.example.LibraryAPI.controllers;

import com.example.LibraryAPI.controllers.DTOs.ErroResponse;
import com.example.LibraryAPI.controllers.DTOs.LivroRequestDTO;
import com.example.LibraryAPI.exceptions.RegistroDuplicadoException;
import com.example.LibraryAPI.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid LivroRequestDTO livroDto){
        try{
            return ResponseEntity.ok(livroDto);
        }catch (RegistroDuplicadoException erro){
            ErroResponse erroDto = ErroResponse.conflito(erro.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }
}
