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
    public ResponseEntity<Object> GetDetailsById(@PathVariable String id){
        UUID idLivro = UUID.fromString(id);
        Optional<Livro> livroOptional = livroService.getById(idLivro);
        if(livroOptional.isPresent()){
            Livro livro = livroOptional.get();
            Autor autor = livro.getAutor();
            AutorDTO dto = autorMapper.toDTO(autor);
            LivroDetailsDTO livroDetailsDTO = new LivroDetailsDTO(
                    livro.getId(),
                    livro.getIsbn(),
                    livro.getTittle(),
                    livro.getDataPublicacao(),
                    livro.getGenero(),
                    livro.getPreco(),
                    dto);

            return ResponseEntity.status(HttpStatus.OK).body(livroDetailsDTO);
        }

        return ResponseEntity.notFound().build();
    }
}
