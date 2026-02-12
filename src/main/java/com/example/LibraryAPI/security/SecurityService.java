package com.example.LibraryAPI.security;

import com.example.LibraryAPI.model.Usuario;
import com.example.LibraryAPI.repository.UsuarioRepository;
import com.example.LibraryAPI.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;

    public Usuario obterUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String login =  userDetails.getUsername();

        return usuarioService.getByLogin(login);
    }
}
