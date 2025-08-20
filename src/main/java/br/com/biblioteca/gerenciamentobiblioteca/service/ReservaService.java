package br.com.biblioteca.gerenciamentobiblioteca.service;

import br.com.biblioteca.gerenciamentobiblioteca.enumerator.StatusLivro;
import br.com.biblioteca.gerenciamentobiblioteca.model.Livro;
import br.com.biblioteca.gerenciamentobiblioteca.model.Reserva;
import br.com.biblioteca.gerenciamentobiblioteca.model.Usuario;
import br.com.biblioteca.gerenciamentobiblioteca.repository.LivroRepository;
import br.com.biblioteca.gerenciamentobiblioteca.repository.ReservaRepository;
import br.com.biblioteca.gerenciamentobiblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ReservaService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Transactional
    public Reserva realizarReserva(Long usuarioId, Long livroId) {
        // Validar se o usuário existe
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        // Validar se o livro existe
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado."));

        // Validar se o livro está disponível
        if (livro.getStatus() != StatusLivro.DISPONIVEL) {
            throw new IllegalStateException("O livro não está disponível para reserva.");
        }

        // Efetivar a reserva
        livro.setStatus(StatusLivro.RESERVADO);
        livroRepository.save(livro);

        Reserva novaReserva = new Reserva(usuario, livro, LocalDate.now());
        return reservaRepository.save(novaReserva);
    }
}