package com.example.LibraryAPI.services;

import com.example.LibraryAPI.model.Autor;
import com.example.LibraryAPI.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor salvar(Autor autor){
        return autorRepository.save(autor);
    }

    public Optional<Autor> getById(UUID id){
        return autorRepository.findById(id);
    }

    public void deleteById(Autor autor){
        autorRepository.delete(autor);
    }
}
