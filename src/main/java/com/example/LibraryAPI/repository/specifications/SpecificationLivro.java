package com.example.LibraryAPI.repository.specifications;

import com.example.LibraryAPI.model.GeneroLivro;
import com.example.LibraryAPI.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationLivro {


    public static Specification<Livro> isbnEqual(String isbn){
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tittleLike (String tittle){
        return ((root, query, cb) -> cb.like( cb.upper(root.get("tittle")),"%" + tittle.toUpperCase() + "%"));
    }

    public static Specification<Livro> generoEqual(GeneroLivro generoLivro){
        return ((root, query, cb) -> cb.equal(root.get("genero"), generoLivro));
    }
}
