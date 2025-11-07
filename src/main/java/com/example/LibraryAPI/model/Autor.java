package com.example.LibraryAPI.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_autor")
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "Nome", length = 150, nullable = false)
    private String nome;

    @OneToMany
    private List<Livro> livros;
}
