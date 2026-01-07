package com.example.LibraryAPI.controllers.mappers;

import com.example.LibraryAPI.controllers.DTOs.AutorDTO;
import com.example.LibraryAPI.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {
    Autor toAntity(AutorDTO autorDTO);

    AutorDTO toDTO(Autor autor);
}
