package com.example.LibraryAPI.controllers;

import com.example.LibraryAPI.controllers.DTOs.LivroRequestDTO;
import com.example.LibraryAPI.controllers.mappers.LivroMapper;
import com.example.LibraryAPI.model.Livro;
import com.example.LibraryAPI.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController{

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid LivroRequestDTO livroDto){
        Livro livro = livroMapper.toEntity(livroDto);
        livroService.salvar(livro);

        URI url = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(url).build();
    }
}
