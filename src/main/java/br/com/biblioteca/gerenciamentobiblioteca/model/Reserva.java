package br.com.biblioteca.gerenciamentobiblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    private LocalDate dataReserva;

    public Reserva(Usuario usuario, Livro livro, LocalDate dataReserva) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataReserva = dataReserva;
    }
}