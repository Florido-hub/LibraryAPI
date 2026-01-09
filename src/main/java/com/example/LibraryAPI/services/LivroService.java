package com.example.LibraryAPI.services;

import com.example.LibraryAPI.model.GeneroLivro;
import com.example.LibraryAPI.model.Livro;
import com.example.LibraryAPI.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;


    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }


    public Optional<Livro> getById(UUID id) {
        return livroRepository.findById(id);
    }

    public void delete(Livro livro) {
        livroRepository.delete(livro);
    }

    //isbn, tittle, nomeAutor, genero, anoDePublicacao
    public List<Livro> search(
            String isbn, String nomeAutor, GeneroLivro generoLivro, Integer anoPublicacao) {

        Specification<Livro> specification = null;
        /***
         * Criterios
         */
        return livroRepository.findAll(specification);
    }
}
