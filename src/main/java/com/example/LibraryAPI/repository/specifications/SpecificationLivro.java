package com.example.LibraryAPI.repository.specifications;

import com.example.LibraryAPI.model.GeneroLivro;
import com.example.LibraryAPI.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao){
        // and to_char(data_publicacao, 'YYYY') = :anoPublicacao
        return ((root, query, cb) ->
                cb.equal( cb.function("to_char", String.class, root.get("dataPublicacao"), cb.literal("YYYY")),
                        anoPublicacao.toString()));
    }

    public static Specification<Livro> nomeAutorLike(String nomeAutor){
        return (root, query, cb) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return cb.like( cb.upper(joinAutor.get("nome")), "%" + nomeAutor.toUpperCase() + "%");

//            return cb.like( cb.upper(root.get("autor").get("nome")), "%" + nomeAutor.toUpperCase() + "%");
        };
    }


}
