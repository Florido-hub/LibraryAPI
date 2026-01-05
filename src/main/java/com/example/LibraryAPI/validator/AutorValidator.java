package com.example.LibraryAPI.validator;

import com.example.LibraryAPI.exceptions.RegistroDuplicadoException;
import com.example.LibraryAPI.model.Autor;
import com.example.LibraryAPI.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AutorValidator {

    private final AutorRepository repository;

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor))
            throw  new RegistroDuplicadoException("Autor já cadastrado!");
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = repository
                .findByNomeAndDataNascimentoAndNacionalidade(
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade()
                );
        if(autorEncontrado.isEmpty())
            return false;

        if(autor.getId() == null){
            return true;
        }

        return !autor.getId().equals(autorEncontrado.get().getId());
    }
}
