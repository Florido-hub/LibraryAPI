package com.example.LibraryAPI.repository;

import com.example.LibraryAPI.model.Autor;
import com.example.LibraryAPI.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */
@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query method
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTittle(String tittle);

    List<Livro> findByisbn(String isbn);

    List<Livro> findByTittleAndPreco(String Tittle, BigDecimal preco);

    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    // JPQL -> referencia as entidades e as propriedades
    // SQL: select l.* from livro as l order by l.tittle
    @Query("select l from Livro as l order by l.tittle, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    // SQL: select a.* from livro join a on a.id = l.id_autor
    @Query("select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    // SQL: select distinct l.* from Livro l
    @Query("select distinct l.tittle from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
        select l.genero
        from Livro l
        join l.autor a
        where a.nacionalidade = 'brasileira'
        order by l.genero
        """)
    List<String> listarGenerosAutoresBrasileiros();
}
