package com.example.LibraryAPI.services;

import com.example.LibraryAPI.model.Usuario;
import com.example.LibraryAPI.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public void save(Usuario usuario) {
        String password = usuario.getSenha();

        usuario.setSenha(encoder.encode(password));
        usuarioRepository.save(usuario);
    }

    public Usuario getByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}
