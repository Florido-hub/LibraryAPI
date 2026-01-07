package com.example.LibraryAPI.services;

import com.example.LibraryAPI.model.Livro;
import com.example.LibraryAPI.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;


    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }
}
