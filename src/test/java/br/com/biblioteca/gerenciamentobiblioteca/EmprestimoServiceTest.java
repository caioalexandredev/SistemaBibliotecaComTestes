package br.com.biblioteca.gerenciamentobiblioteca;

import br.com.biblioteca.gerenciamentobiblioteca.model.Emprestimo;
import br.com.biblioteca.gerenciamentobiblioteca.model.Endereco;
import br.com.biblioteca.gerenciamentobiblioteca.model.Livro;
import br.com.biblioteca.gerenciamentobiblioteca.model.Usuario;
import br.com.biblioteca.gerenciamentobiblioteca.repository.EmprestimoRepository;
import br.com.biblioteca.gerenciamentobiblioteca.repository.LivroRepository;
import br.com.biblioteca.gerenciamentobiblioteca.repository.UsuarioRepository;
import br.com.biblioteca.gerenciamentobiblioteca.service.EmprestimoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmprestimoServiceTest {

    @Autowired
    private EmprestimoService emprestimoService;

    @Autowired
    private EmprestimoRepository emprestimoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LivroRepository livroRepository;

    private Emprestimo emprestimoValido;

    @BeforeEach
    void setUp() {
        emprestimoRepository.deleteAll();
        usuarioRepository.deleteAll();
        livroRepository.deleteAll();

        Usuario usuarioNovo = new Usuario("Ana", "ana@email.com");
        Endereco endereco = new Endereco(null, "Rua das Flores", "São Paulo", "SP", "01000-000");
        usuarioNovo.setEndereco(endereco);

        Usuario usuario = usuarioRepository.save(usuarioNovo);
        Livro livro = livroRepository.save(new Livro("Duna", "Frank Herbert", "Ficção Científica"));

        emprestimoValido = new Emprestimo();
        emprestimoValido.setUsuario(usuario);
        emprestimoValido.setLivro(livro);
        emprestimoValido.setDataEmprestimo(LocalDate.now().minusDays(10));
        emprestimoValido.setDataVencimento(LocalDate.now().plusDays(4)); // Vence em 4 dias
        emprestimoValido.setNumeroRenovacoes(0);
        emprestimoValido = emprestimoRepository.save(emprestimoValido);
    }

    @Test
    @DisplayName("Deve renovar um empréstimo com sucesso se estiver dentro do prazo e do limite")
    void deveRenovarEmprestimoComSucesso() {
        Emprestimo emprestimoRenovado = emprestimoService.renovarEmprestimo(emprestimoValido.getId());

        assertNotNull(emprestimoRenovado);
        assertEquals(1, emprestimoRenovado.getNumeroRenovacoes());

        assertTrue(emprestimoRenovado.getDataVencimento().isAfter(emprestimoValido.getDataVencimento()));
        assertEquals(emprestimoValido.getDataVencimento().plusDays(14), emprestimoRenovado.getDataVencimento());
    }

    @Test
    @DisplayName("Não deve renovar um empréstimo após a data de vencimento")
    void naoDeveRenovarEmprestimoVencido() {
        emprestimoValido.setDataVencimento(LocalDate.now().minusDays(1));
        emprestimoRepository.save(emprestimoValido);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            emprestimoService.renovarEmprestimo(emprestimoValido.getId());
        });

        assertEquals("Não é possível renovar um empréstimo vencido.", exception.getMessage());
    }

    @Test
    @DisplayName("Não deve renovar um empréstimo que atingiu o limite de renovações")
    void naoDeveRenovarEmprestimoComLimiteAtingido() {
        emprestimoValido.setNumeroRenovacoes(2);
        emprestimoRepository.save(emprestimoValido);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            emprestimoService.renovarEmprestimo(emprestimoValido.getId());
        });

        assertEquals("Limite máximo de renovações atingido.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar renovar empréstimo inexistente")
    void deveLancarExcecaoAoTentarRenovarEmprestimoInexistente() {
        Long idInexistente = 999L;
        assertThrows(IllegalArgumentException.class, () -> {
            emprestimoService.renovarEmprestimo(idInexistente);
        });
    }
}