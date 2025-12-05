package com.example.LibraryAPI.repository;

import com.example.LibraryAPI.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("brasileira");
        autor.setDataNascimento(LocalDate.of(1950,1,31));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo:" + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("");

        Optional<Autor> possivelAutor = repository.findById();

        if(possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor");
            System.out.println(possivelAutor.get());

            autorEncontrado.setDataNascimento(LocalDate.of(1950,1,31));

            repository.save(autorEncontrado);
        }
    }
}
