package br.com.biblioteca.gerenciamentobiblioteca.model;

import br.com.biblioteca.gerenciamentobiblioteca.enumerator.StatusLivro;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;
    private String categoria;

    @Enumerated(EnumType.STRING) // Grava o nome do enum ("DISPONIVEL") no banco, que é mais legível
    private StatusLivro status = StatusLivro.DISPONIVEL;

    public Livro(String titulo, String autor, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.status = StatusLivro.DISPONIVEL;
    }

    public Livro(String titulo, String autor, String categoria, StatusLivro status) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.status = status;
    }
}