package com.example.LibraryAPI.repository;

import com.example.LibraryAPI.model.Autor;
import com.example.LibraryAPI.model.GeneroLivro;
import com.example.LibraryAPI.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("3791-2313");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTittle("Interestelar");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        Autor autor = autorRepository
                .findById(UUID.fromString("84db3b4a-fccd-4910-bbbe-7c081f7577d1")).
                orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }
}