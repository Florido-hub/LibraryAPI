package com.example.LibraryAPI.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "tittle", length = 150, nullable = false)
    private String tittle;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Column(name = "genero", length = 30, nullable = false)
    private String genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
