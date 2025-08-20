package br.com.biblioteca.gerenciamentobiblioteca.service;

import br.com.biblioteca.gerenciamentobiblioteca.model.Usuario;
import br.com.biblioteca.gerenciamentobiblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("E-mail já cadastrado no sistema.");
        }

        return usuarioRepository.save(usuario);
    }
}