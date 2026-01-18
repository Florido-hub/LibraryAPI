package com.example.LibraryAPI.services;

import com.example.LibraryAPI.model.GeneroLivro;
import com.example.LibraryAPI.model.Livro;
import com.example.LibraryAPI.repository.LivroRepository;
import com.example.LibraryAPI.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final LivroValidator validator;


    public void salvar(Livro livro) {
        validator.validar(livro);
        livroRepository.save(livro);
    }

    public void update(Livro livro) {
        if(livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o livro ja esteja salvo na BD");
        }
        validator.validar(livro);
        livroRepository.save(livro);
    }


    public Optional<Livro> getById(UUID id) {
        return livroRepository.findById(id);
    }

    public void delete(Livro livro) {
        livroRepository.delete(livro);
    }

    //isbn, tittle, nomeAutor, genero, anoDePublicacao
    public Page<Livro> searchBySpecification(
            String isbn, String tittle, String nomeAutor, GeneroLivro generoLivro, Integer anoPublicacao, Integer pagina, Integer tamanhoPagina) {

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

        if(anoPublicacao != null){
            specification = specification.and(anoPublicacaoEqual(anoPublicacao));
        }

        if(nomeAutor != null){
            specification = specification.and(nomeAutorLike(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return livroRepository.findAll(specification, pageRequest);
    }

    public Page<Livro> getAll(Integer pagina, Integer tamanhoPagina) {
        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return livroRepository.findAll(pageRequest);
    }
}
