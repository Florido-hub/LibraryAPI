package com.example.LibraryAPI.repository;

import com.example.LibraryAPI.model.Autor;
import com.example.LibraryAPI.model.GeneroLivro;
import com.example.LibraryAPI.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("josé");
        autor.setNacionalidade("brasileira");
        autor.setDataNascimento(LocalDate.of(1950,1,31));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo:" + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("84db3b4a-fccd-4910-bbbe-7c081f7577d1");

        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor");
            System.out.println(possivelAutor.get());

            autorEncontrado.setDataNascimento(LocalDate.of(1950,1,31));

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("84db3b4a-fccd-4910-bbbe-7c081f7577d1");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("84db3b4a-fccd-4910-bbbe-7c081f7577d1");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1970,8,5));

        Livro livro = new Livro();
        livro.setIsbn("3341-2313");
        livro.setPreco(BigDecimal.valueOf(230));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTittle("Interestelar");
        livro.setDataPublicacao(LocalDate.of(2002,3,10));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("7641-2313");
        livro2.setPreco(BigDecimal.valueOf(190));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTittle("O Iluminado");
        livro2.setDataPublicacao(LocalDate.of(1998,11,24));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }
}
