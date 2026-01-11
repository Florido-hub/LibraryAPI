package com.example.LibraryAPI.validator;

import com.example.LibraryAPI.exceptions.IsbnDuplicadoException;
import com.example.LibraryAPI.model.Livro;
import com.example.LibraryAPI.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository livroRepository;

    public void validar(Livro livro) {
        if(isbnExiste(livro)){
            throw new IsbnDuplicadoException("ISBN duplicado");
        }
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
