package com.example.LibraryAPI.controllers;

import com.example.LibraryAPI.controllers.DTOs.AutorDTO;
import com.example.LibraryAPI.controllers.mappers.AutorMapper;
import com.example.LibraryAPI.model.Autor;
import com.example.LibraryAPI.services.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores") // http://localhost:8080/autores
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;


    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> save(@RequestBody @Valid AutorDTO dto) {
        var autor = autorMapper.toAntity(dto);
        autorService.salvar(autor);

        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();
    }

    // http://localhost:8080/autores/id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    public ResponseEntity<AutorDTO> getDetailsById(@PathVariable String id) {
        var idAutor = UUID.fromString(id);

        return autorService
                .getById(idAutor)
                .map(autor -> {
                    AutorDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());


        /***
         * if (autorOptional.isPresent()) {
         *             Autor autor = autorOptional.get();
         *             AutorDTO dto = autorMapper.toDTO(autor);
         *             return ResponseEntity.ok(dto);
         *         }
         *  return ResponseEntity.notFount().build());
         */
    }

    // http://localhost:8080/autores/id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.getById(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deleteById(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    // http://localhost:8080/autores?nome=fulano&nacionalidade=brasileiro
    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultado = autorService.searchByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(autorMapper::toDTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    // http://localhost:8080/autores/id
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> update(
            @PathVariable String id,
            @RequestBody @Valid AutorDTO autorDTO) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.getById(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Autor autor = autorOptional.get();
        autor.setNome(autorDTO.nome());
        autor.setDataNascimento(autorDTO.dataNascimento());
        autor.setNacionalidade(autorDTO.nacionalidade());

        autorService.update(autor);

        return ResponseEntity.noContent().build();
    }
}
