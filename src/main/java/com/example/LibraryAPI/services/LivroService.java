package com.example.LibraryAPI.services;

import com.example.LibraryAPI.model.GeneroLivro;
import com.example.LibraryAPI.model.Livro;
import com.example.LibraryAPI.repository.LivroRepository;
import com.example.LibraryAPI.repository.specifications.SpecificationLivro;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.LibraryAPI.repository.specifications.SpecificationLivro.*;

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
            String isbn, String tittle, String nomeAutor, GeneroLivro generoLivro, Integer anoPublicacao) {

//        Specification<Livro> specification = Specification
//                .where(SpecificationLivro.isbnEqual(isbn))
//                .and(SpecificationLivro.tittleLike(tittle))
//                .and(SpecificationLivro.generoEqual(generoLivro));

        Specification<Livro> specification = Specification.where(((root, query, cb) -> cb.conjunction()));

        if(isbn != null){
            specification = specification.and(isbnEqual(isbn));
        }

        if(tittle != null){
            specification = specification.and(tittleLike(tittle));
        }

        if(generoLivro != null){
            specification = specification.and(generoEqual(generoLivro));
        }

        return livroRepository.findAll(specification);
    }
}
