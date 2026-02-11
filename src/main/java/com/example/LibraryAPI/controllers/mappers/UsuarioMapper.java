package com.example.LibraryAPI.controllers.mappers;

import com.example.LibraryAPI.controllers.DTOs.UsuarioDTO;
import com.example.LibraryAPI.controllers.DTOs.UsuarioResponseDTO;
import com.example.LibraryAPI.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDTO(Usuario usuario);

    Usuario toEntity(UsuarioDTO dto);

    UsuarioResponseDTO toResponseDTO(Usuario usuario);
}
