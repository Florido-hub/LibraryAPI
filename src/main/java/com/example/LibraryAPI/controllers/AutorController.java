package com.example.LibraryAPI.controllers;

import com.example.LibraryAPI.DTOs.AutorDTO;
import com.example.LibraryAPI.DTOs.ErroResponse;
import com.example.LibraryAPI.exceptions.OperacaoNaoPermitida;
import com.example.LibraryAPI.exceptions.RegistroDuplicadoException;
import com.example.LibraryAPI.model.Autor;
import com.example.LibraryAPI.services.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores") // http://localhost:8080/autores
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;


    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid AutorDTO autor){
        try {
            var autorEntidade = autor.mapearParaAutor();
            autorService.salvar(autorEntidade);

            // http://localhost:8080/autores/id
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();
            // Heater Location


            return ResponseEntity.created(location).build();
        }catch (RegistroDuplicadoException erro){
            var erroDTO = ErroResponse.conflito(erro.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    // http://localhost:8080/autores/id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.getById(idAutor);
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    // http://localhost:8080/autores/id
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable String id){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.getById(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            autorService.deleteById(autorOptional.get());
            return ResponseEntity.noContent().build();
        }catch (OperacaoNaoPermitida erro){
            var erroResposta = ErroResponse.respostaPadrao(erro.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    // http://localhost:8080/autores?nome=fulano&nacionalidade=brasileiro
    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){

        List<Autor> resultado = autorService.searchByExample(nome,nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())
                ).toList();
        return ResponseEntity.ok(lista);
    }

    // http://localhost:8080/autores/id
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable String id,
            @RequestBody @Valid AutorDTO autorDTO){
        try {

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
        }catch (RegistroDuplicadoException erro){
            var erroDTO = ErroResponse.conflito(erro.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
