package com.example.LibraryAPI.controllers.mappers;

import com.example.LibraryAPI.controllers.DTOs.AutorDTO;
import com.example.LibraryAPI.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    @Mapping(source = "nacionalidade", target = "nacionalidade")
    Autor toAntity(AutorDTO autorDTO);

    AutorDTO toDTO(Autor autor);
}
