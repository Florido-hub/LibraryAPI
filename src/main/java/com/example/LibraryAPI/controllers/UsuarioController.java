package com.example.LibraryAPI.controllers;

import com.example.LibraryAPI.controllers.DTOs.UsuarioDTO;
import com.example.LibraryAPI.controllers.DTOs.UsuarioResponseDTO;
import com.example.LibraryAPI.controllers.mappers.UsuarioMapper;
import com.example.LibraryAPI.model.Usuario;
import com.example.LibraryAPI.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody UsuarioDTO dto) {
        Usuario entity = usuarioMapper.toEntity(dto);
        usuarioService.save(entity);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        List<UsuarioResponseDTO> usuarios = usuarioService.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO).toList();

        return ResponseEntity.ok(usuarios);
    }
}

