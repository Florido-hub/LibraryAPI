package com.example.LibraryAPI.controllers;

import com.example.LibraryAPI.controllers.DTOs.AutorDTO;
import com.example.LibraryAPI.controllers.DTOs.LivroDetailsDTO;
import com.example.LibraryAPI.controllers.DTOs.LivroRequestDTO;
import com.example.LibraryAPI.controllers.mappers.AutorMapper;
import com.example.LibraryAPI.controllers.mappers.LivroMapper;
import com.example.LibraryAPI.model.Autor;
import com.example.LibraryAPI.model.Livro;
import com.example.LibraryAPI.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController{

    private final LivroService livroService;
    private final LivroMapper livroMapper;
    private final AutorMapper autorMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid LivroRequestDTO livroDto){
        Livro livro = livroMapper.toEntity(livroDto);
        livroService.salvar(livro);

        URI url = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDetailsDTO> GetDetailsById(@PathVariable String id){
        return livroService.getById(UUID.fromString(id))
                .map(livro -> {
                    LivroDetailsDTO dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
