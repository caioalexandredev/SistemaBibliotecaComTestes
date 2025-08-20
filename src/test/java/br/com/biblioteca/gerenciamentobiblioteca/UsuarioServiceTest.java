package br.com.biblioteca.gerenciamentobiblioteca;

import br.com.biblioteca.gerenciamentobiblioteca.model.Endereco;
import br.com.biblioteca.gerenciamentobiblioteca.model.Usuario;
import br.com.biblioteca.gerenciamentobiblioteca.service.UsuarioService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Deve cadastrar um usuário com sucesso quando os dados são válidos")
    void deveCadastrarUsuarioComSucesso() {
        Endereco novoEndereco = new Endereco(null, "Rua das Flores", "São Paulo", "SP", "01000-000");
        Usuario novoUsuario = new Usuario("João Silva", "joao.silva@example.com");
        novoUsuario.setEndereco(novoEndereco);

        Usuario usuarioSalvo = usuarioService.cadastrarUsuario(novoUsuario);

        assertNotNull(usuarioSalvo); // Verifica se o usuário retornado não é nulo
        assertNotNull(usuarioSalvo.getId()); // Verifica se o usuário salvo tem um ID
        assertEquals("João Silva", usuarioSalvo.getNome()); // Verifica se o nome está correto
        assertEquals("joao.silva@example.com", usuarioSalvo.getEmail()); // Verifica o email
        assertNotNull(usuarioSalvo.getEndereco()); // Verificamos se o endereço foi salvo
        assertNotNull(usuarioSalvo.getEndereco().getId()); // E se o endereço tem um ID
        assertEquals("Rua das Flores", usuarioSalvo.getEndereco().getRua()); // Verificamos um campo do endereço
    }

    @Test
    @DisplayName("Não deve cadastrar usuário com e-mail duplicado")
    void naoDeveCadastrarUsuarioComEmailDuplicado() {
        Endereco enderecoExistente = new Endereco(null, "Avenida Principal", "Rio de Janeiro", "RJ", "20000-000");
        Usuario usuarioExistente = new Usuario("Maria Santos", "maria.santos@example.com");
        usuarioExistente.setEndereco(enderecoExistente);
        usuarioService.cadastrarUsuario(usuarioExistente);

        Endereco outroEndereco = new Endereco(null, "Outra Rua", "Outra Cidade", "XX", "99999-999");
        Usuario usuarioComEmailDuplicado = new Usuario("Outro Nome", "maria.santos@example.com");
        usuarioComEmailDuplicado.setEndereco(outroEndereco);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.cadastrarUsuario(usuarioComEmailDuplicado);
        }); // Captura o erro para análise do teste

        assertEquals("E-mail já cadastrado no sistema.", exception.getMessage()); // Verificamos se a mensagem de erro correta foi exibida
    }


    @ParameterizedTest
    @ValueSource(strings = {"email-invalido", "email@.com", "@dominio.com", ""})
    @DisplayName("Não deve cadastrar usuário com formato de e-mail inválido")
    void naoDeveCadastrarUsuarioComEmailDeFormatoInvalido(String emailInvalido) {
        Endereco endereco = new Endereco(null, "Avenida JK", "Palmas", "TO", "77000-000");
        Usuario usuarioComEmailInvalido = new Usuario("Caio Alexandre de Sousa Ramos", emailInvalido);
        usuarioComEmailInvalido.setEndereco(endereco);

        assertThrows(ConstraintViolationException.class, () -> {
            usuarioService.cadastrarUsuario(usuarioComEmailInvalido);
        }); //Verifica se um erro foi gerado
    }
}