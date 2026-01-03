package com.example.LibraryAPI.services;

import com.example.LibraryAPI.exceptions.OperacaoNaoPermitida;
import com.example.LibraryAPI.model.Autor;
import com.example.LibraryAPI.repository.AutorRepository;
import com.example.LibraryAPI.repository.LivroRepository;
import com.example.LibraryAPI.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.AnnotatedArrayType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    public Autor salvar(Autor autor){
        validator.validar(autor);
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

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
