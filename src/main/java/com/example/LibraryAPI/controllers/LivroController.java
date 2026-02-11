package com.example.LibraryAPI.controllers;

import com.example.LibraryAPI.controllers.DTOs.LivroDetailsDTO;
import com.example.LibraryAPI.controllers.DTOs.LivroMinDTO;
import com.example.LibraryAPI.controllers.DTOs.LivroRequestDTO;
import com.example.LibraryAPI.controllers.mappers.LivroMapper;
import com.example.LibraryAPI.model.GeneroLivro;
import com.example.LibraryAPI.model.Livro;
import com.example.LibraryAPI.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController{

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid LivroRequestDTO livroDto){
        Livro livro = livroMapper.toEntity(livroDto);
        livroService.salvar(livro);

        URI url = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(url).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Page<LivroMinDTO>> getAll(Pageable pageable){
        Page<Livro> livros = livroService.getAll(pageable);
        Page<LivroMinDTO> pageLivros = livros.map(livroMapper::toMinDTO);

        return ResponseEntity.ok(pageLivros);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<LivroDetailsDTO> GetDetailsById(@PathVariable String id){
        return livroService.getById(UUID.fromString(id))
                .map(livro -> {
                    LivroDetailsDTO dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> delete(@PathVariable String id){
        UUID idLivro = UUID.fromString(id);

        return livroService.getById(idLivro)
                .map(livro -> {
                    livroService.delete(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Page<LivroDetailsDTO>> search(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "tittle", required = false) String tittle,
            @RequestParam(value = "nome_autor", required = false) String nomeAutor,
            @RequestParam(value = "genero", required = false) GeneroLivro generoLivro,
            @RequestParam(value = "ano_publicacao", required = false) Integer anoPublicacao,
            Pageable pageable
    ){

        Page<Livro> pageSearch = livroService.searchBySpecification(
                isbn, tittle, nomeAutor, generoLivro, anoPublicacao, pageable);

        Page<LivroDetailsDTO> livroDetailsDTO = pageSearch.map(livroMapper::toDTO);

        return ResponseEntity.ok(livroDetailsDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> update(
            @RequestBody @Valid LivroRequestDTO livroDto,
            @PathVariable String id) {
        return livroService.getById(UUID.fromString(id))
                .map(livro -> {
                    Livro aux = livroMapper.toEntity(livroDto);

                    livro.setIsbn(aux.getIsbn());
                    livro.setTittle(aux.getTittle());
                    livro.setPreco(aux.getPreco());
                    livro.setGenero(aux.getGenero());
                    livro.setDataPublicacao(aux.getDataPublicacao());
                    livro.setAutor(aux.getAutor());

                    livroService.update(livro);

                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
