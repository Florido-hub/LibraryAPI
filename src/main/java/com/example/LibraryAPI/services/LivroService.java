package com.example.LibraryAPI.services;

import com.example.LibraryAPI.exceptions.IsbnDuplicadoException;
import com.example.LibraryAPI.model.Autor;
import com.example.LibraryAPI.model.GeneroLivro;
import com.example.LibraryAPI.model.Livro;
import com.example.LibraryAPI.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.LibraryAPI.repository.specifications.SpecificationLivro.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;


    public Livro salvar(Livro livro) {
        if(isbnDuplicado(livro.getIsbn())){
            throw new IsbnDuplicadoException("ISBN duplicado");
        }
        return livroRepository.save(livro);
    }

    private boolean isbnDuplicado(String isbn) {
        return  livroRepository.existsByIsbn(isbn);
    }

    public void update(Livro livro) {
        if(livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o livro ja esteja salvo na BD");
        }
        livroRepository.save(livro);
    }


    public Optional<Livro> getById(UUID id) {
        return livroRepository.findById(id);
    }

    public void delete(Livro livro) {
        livroRepository.delete(livro);
    }

    //isbn, tittle, nomeAutor, genero, anoDePublicacao
    public List<Livro> searchBySpecification(
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

        if(anoPublicacao != null){
            specification = specification.and(anoPublicacaoEqual(anoPublicacao));
        }

        if(nomeAutor != null){
            specification = specification.and(nomeAutorLike(nomeAutor));
        }

        return livroRepository.findAll(specification);
    }

    public List<Livro> searchByExample(
            String isbn, String tittle, String nomeAutor, GeneroLivro generoLivro, LocalDate dataPublicacao) {
        Livro livro = new Livro();
        livro.setIsbn(isbn);
        livro.setTittle(tittle);
        livro.setGenero(generoLivro);
        livro.setDataPublicacao(dataPublicacao);

        if (nomeAutor != null) {
            Autor autor = new Autor();
            autor.setNome(nomeAutor);
            livro.setAutor(autor);
        }

//              .withIgnorePaths("id", "dataNascimento")
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Livro> livroExample = Example.of(livro, matcher);

        return livroRepository.findAll(livroExample);
    }


}
