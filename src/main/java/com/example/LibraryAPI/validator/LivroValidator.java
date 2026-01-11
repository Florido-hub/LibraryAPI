package com.example.LibraryAPI.validator;

import com.example.LibraryAPI.exceptions.CampoInvalidoException;
import com.example.LibraryAPI.exceptions.IsbnDuplicadoException;
import com.example.LibraryAPI.model.Livro;
import com.example.LibraryAPI.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository livroRepository;


    public void validar(Livro livro) {
        if(isbnExiste(livro)){
            throw new IsbnDuplicadoException("ISBN duplicado");
        }

        if(isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("Preco", "Para livros com ano de publicacao a partir de 2020, o preco eh obrigatorio");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }


    private boolean isbnExiste(Livro livro) {
        Optional<Livro> livroOptional = livroRepository.findByisbn(livro.getIsbn());

        if(livroOptional.isEmpty()){
            return false;
        }

        if(livro.getId() == null){
            return true;
        }

        return !livro.getId().equals(livroOptional.get().getId());
    }
}
