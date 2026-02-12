package com.example.LibraryAPI.services;

import com.example.LibraryAPI.exceptions.OperacaoNaoPermitida;
import com.example.LibraryAPI.model.Autor;
import com.example.LibraryAPI.model.Usuario;
import com.example.LibraryAPI.repository.AutorRepository;
import com.example.LibraryAPI.repository.LivroRepository;
import com.example.LibraryAPI.security.SecurityService;
import com.example.LibraryAPI.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {


    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService  securityService;

    public Autor salvar(Autor autor){
        validator.validar(autor);

        Usuario usuario = securityService.obterUsuario();
        autor.setUsuario(usuario);

        return autorRepository.save(autor);
    }

    public void update(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor ja esteja salvo na BD");
        }
        validator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> getById(UUID id){
        return autorRepository.findById(id);
    }

    public void deleteById(Autor autor){
        if(possuiLivro(autor))
            throw  new OperacaoNaoPermitida("Não eh permitido excluir autor possui livros cadastrados");
        autorRepository.delete(autor);
    }

    public List<Autor> search(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if(nome != null) {
            return autorRepository.findByNome(nome);
        }

        if(nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public List<Autor> searchByExample(String nome, String nacionalidade) {
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "dataNascimento")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);

        return autorRepository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
