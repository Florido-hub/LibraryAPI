package com.example.LibraryAPI.services;

import com.example.LibraryAPI.model.Autor;
import com.example.LibraryAPI.repository.AutorRepository;
import com.example.LibraryAPI.validator.AutorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator validator;

    @Autowired
    public AutorService(AutorRepository autorRepository, AutorValidator validator) {
        this.autorRepository = autorRepository;
        this.validator = validator;
    }

    public Autor salvar(Autor autor){
        validator.validar(autor);
        return autorRepository.save(autor);
    }

    public void update(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor ja esteja salvo na BD");
        }
        autorRepository.save(autor);
    }

    public Optional<Autor> getById(UUID id){
        return autorRepository.findById(id);
    }

    public void deleteById(Autor autor){
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
}
