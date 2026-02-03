package com.example.LibraryAPI.controllers.DTOs;

import java.util.List;

public record UsuarioDTO(
        String login,
        String senha,
        List<String> roles
) {
}
