package com.example.LibraryAPI.repository;

import com.example.LibraryAPI.model.Autor;
import com.example.LibraryAPI.model.GeneroLivro;
import com.example.LibraryAPI.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
        livro.setTittle("2001");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        Autor autor = autorRepository
                .findById(UUID.fromString("84db3b4a-fccd-4910-bbbe-7c081f7577d1")).
                orElse(null);


        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarLivroEAutorTest(){
        Livro livro = new Livro();
        livro.setIsbn("3791-2313");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTittle("2001");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        Autor autor = new Autor();
        autor.setNome("maria");
        autor.setNacionalidade("brasileira");
        autor.setDataNascimento(LocalDate.of(1960,11,24));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("3791-2313");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTittle("2001");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        Autor autor = new Autor();
        autor.setNome("maria");
        autor.setNacionalidade("brasileira");
        autor.setDataNascimento(LocalDate.of(1960,11,24));

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarTest(){
        UUID id = UUID.fromString("84db3b4a-fccd-4910-bbbe-7c081f7577d1");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("84db3b4a-fccd-4910-bbbe-7c081f7577d1");
        Autor AT = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(AT);

        repository.save(livroParaAtualizar);
    }

    @Test
    void delete() {
        UUID id = UUID.fromString("84db3b4a-fccd-4910-bbbe-7c081f7577d1");
        repository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("84db3b4a-fccd-4910-bbbe-7c081f7577d1");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTittle());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor());
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> lista = repository.findByTittle("Interestelar");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest(){
        List<Livro> lista = repository.findByisbn("2134-1241");
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        var resultadoPesquisa = repository.listarTodosOrdenadoPorTituloAndPreco();
        resultadoPesquisa.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros(){
        var resultadoPesquisa = repository.listarAutoresDosLivros();
        resultadoPesquisa.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidosDosLivros(){
        var resultadoPesquisa = repository.listarNomesDiferentesLivros();
        resultadoPesquisa.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeAutoresBrasileiros(){
        var resultadoPesquisa = repository.listarGenerosAutoresBrasileiros();
        resultadoPesquisa.forEach(System.out::println);
    }
}