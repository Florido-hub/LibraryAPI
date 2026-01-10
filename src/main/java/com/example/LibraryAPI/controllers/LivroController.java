package com.example.LibraryAPI.controllers;

import com.example.LibraryAPI.controllers.DTOs.LivroDetailsDTO;
import com.example.LibraryAPI.controllers.DTOs.LivroRequestDTO;
import com.example.LibraryAPI.controllers.mappers.LivroMapper;
import com.example.LibraryAPI.model.GeneroLivro;
import com.example.LibraryAPI.model.Livro;
import com.example.LibraryAPI.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController{

    private final LivroService livroService;
    private final LivroMapper livroMapper;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id){
        UUID idLivro = UUID.fromString(id);

        return livroService.getById(idLivro)
                .map(livro -> {
                    livroService.delete(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LivroDetailsDTO>> search(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "tittle", required = false) String tittle,
            @RequestParam(value = "nome_autor", required = false) String nomeAutor,
            @RequestParam(value = "genero", required = false) GeneroLivro generoLivro,
            @RequestParam(value = "ano_publicacao", required = false) Integer anoPublicacao
    ){
        List<Livro> search = livroService.searchBySpecification(isbn, tittle, nomeAutor, generoLivro, anoPublicacao);
        var listaDTO = search
                .stream()
                .map(livroMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @RequestBody @Valid LivroRequestDTO livroDto,
            @PathVariable String id){
        UUID idLivro = UUID.fromString(id);
        Optional<Livro> livroOptional = livroService.getById(idLivro);

        if(livroOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Livro livro = livroOptional.get();
        Livro livroAtualizado = livroMapper.toEntity(livroDto);
        livroAtualizado.setId(livro.getId());

        livroService.update(livroAtualizado);

        return ResponseEntity.noContent().build();
    }
}
