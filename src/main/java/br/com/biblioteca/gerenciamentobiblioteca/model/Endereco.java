package br.com.biblioteca.gerenciamentobiblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A rua não pode estar em branco.")
    private String rua;

    @NotBlank(message = "A cidade não pode estar em branco.")
    private String cidade;

    @NotBlank(message = "O estado não pode estar em branco.")
    private String estado;

    @NotBlank(message = "O CEP não pode estar em branco.")
    private String cep;
}