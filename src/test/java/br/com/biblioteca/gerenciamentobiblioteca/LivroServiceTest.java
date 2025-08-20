package br.com.biblioteca.gerenciamentobiblioteca;

import br.com.biblioteca.gerenciamentobiblioteca.model.Livro;
import br.com.biblioteca.gerenciamentobiblioteca.repository.EmprestimoRepository;
import br.com.biblioteca.gerenciamentobiblioteca.repository.LivroRepository;
import br.com.biblioteca.gerenciamentobiblioteca.repository.UsuarioRepository;
import br.com.biblioteca.gerenciamentobiblioteca.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroServiceTest {

    @Autowired
    private LivroService livroService;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        emprestimoRepository.deleteAll();
        usuarioRepository.deleteAll();
        livroRepository.deleteAll();

        // Insere alguns livros de exemplo
        livroRepository.save(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "Fantasia"));
        livroRepository.save(new Livro("O Hobbit", "J.R.R. Tolkien", "Fantasia"));
        livroRepository.save(new Livro("1984", "George Orwell", "Ficção Distópica"));
    }

    @Test
    @DisplayName("Deve encontrar livros por autor")
    void deveEncontrarLivrosPorAutor() {
        List<Livro> resultado = livroService.pesquisarLivros(null, "J.R.R. Tolkien", null);
        assertFalse(resultado.isEmpty());
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Deve encontrar livros por categoria")
    void deveEncontrarLivrosPorCategoria() {
        List<Livro> resultado = livroService.pesquisarLivros(null, null, "Fantasia");
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Deve encontrar livros por título parcial ignorando case")
    void deveEncontrarLivrosPorTituloParcialIgnorandoCase() {
        List<Livro> resultado = livroService.pesquisarLivros("senhor dos anéis", null, null);
        assertEquals(1, resultado.size());
        assertEquals("O Senhor dos Anéis", resultado.get(0).getTitulo());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nenhum livro for encontrado")
    void deveRetornarListaVaziaQuandoNenhumLivroForEncontrado() {
        List<Livro> resultado = livroService.pesquisarLivros("Livro que não existe", null, null);
        assertTrue(resultado.isEmpty());
    }
}