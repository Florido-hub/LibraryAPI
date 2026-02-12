package com.example.LibraryAPI.controllers.DTOs;

import java.util.List;

public record UsuarioResponseDTO(
        String login,
        List<String> roles
) {
}
