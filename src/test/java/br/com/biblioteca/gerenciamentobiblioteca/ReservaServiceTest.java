package br.com.biblioteca.gerenciamentobiblioteca;

import br.com.biblioteca.gerenciamentobiblioteca.enumerator.StatusLivro;
import br.com.biblioteca.gerenciamentobiblioteca.model.Endereco;
import br.com.biblioteca.gerenciamentobiblioteca.model.Livro;
import br.com.biblioteca.gerenciamentobiblioteca.model.Reserva;
import br.com.biblioteca.gerenciamentobiblioteca.model.Usuario;
import br.com.biblioteca.gerenciamentobiblioteca.repository.LivroRepository;
import br.com.biblioteca.gerenciamentobiblioteca.repository.UsuarioRepository;
import br.com.biblioteca.gerenciamentobiblioteca.service.ReservaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReservaServiceTest {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    private Usuario usuarioCadastrado;
    private Livro livroDisponivel;
    private Livro livroIndisponivel;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
        livroRepository.deleteAll();

        Usuario usuario = new Usuario("Carlos", "carlos@email.com");
        Endereco endereco = new Endereco(null, "Avenida JK", "Palmas", "TO", "77000-000");
        usuario.setEndereco(endereco);

        usuarioCadastrado = usuarioRepository.save(usuario);
        livroDisponivel = livroRepository.save(new Livro("A Arte da Guerra", "Sun Tzu", "Estratégia", StatusLivro.DISPONIVEL));
        livroIndisponivel = livroRepository.save(new Livro("O Príncipe", "Maquiavel", "Política", StatusLivro.RESERVADO));
    }

    @Test
    @DisplayName("Deve reservar um livro disponível para um usuário cadastrado com sucesso")
    void deveReservarLivroDisponivelParaUsuarioCadastradoComSucesso() {
        Reserva reserva = reservaService.realizarReserva(usuarioCadastrado.getId(), livroDisponivel.getId());

        assertNotNull(reserva);
        assertNotNull(reserva.getId());
        assertEquals(usuarioCadastrado.getId(), reserva.getUsuario().getId());
        assertEquals(livroDisponivel.getId(), reserva.getLivro().getId());

        Livro livroReservado = livroRepository.findById(livroDisponivel.getId()).get();
        assertFalse(livroReservado.getStatus() == StatusLivro.DISPONIVEL); // Verifica se o status disponivel está atribuido
    }

    @Test
    @DisplayName("Não deve permitir a reserva de um livro indisponível")
    void naoDevePermitirReservaDeLivroIndisponivel() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            reservaService.realizarReserva(usuarioCadastrado.getId(), livroIndisponivel.getId());
        });

        // Verificamos se a exceção correta foi lançada
        assertEquals("O livro não está disponível para reserva.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar reservar com um usuário inexistente")
    void deveLancarExcecaoAoTentarReservarComUsuarioInexistente() {
        Long usuarioIdInexistente = 999L;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservaService.realizarReserva(usuarioIdInexistente, livroDisponivel.getId());
        });

        // Verificamos se a exceção correta foi lançada
        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar reservar um livro inexistente")
    void deveLancarExcecaoAoTentarReservarUmLivroInexistente() {
        Long livroIdInexistente = 999L;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservaService.realizarReserva(usuarioCadastrado.getId(), livroIdInexistente);
        });

        // Verificamos se a exceção correta foi lançada
        assertEquals("Livro não encontrado.", exception.getMessage());
    }
}