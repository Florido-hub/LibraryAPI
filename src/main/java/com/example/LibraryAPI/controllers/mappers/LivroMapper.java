package com.example.LibraryAPI.controllers.mappers;

import com.example.LibraryAPI.controllers.DTOs.LivroDetailsDTO;
import com.example.LibraryAPI.controllers.DTOs.LivroRequestDTO;
import com.example.LibraryAPI.model.Livro;
import com.example.LibraryAPI.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null))")
    public abstract Livro toEntity(LivroRequestDTO dto);

    public abstract LivroDetailsDTO toDTO(Livro livro);
}
