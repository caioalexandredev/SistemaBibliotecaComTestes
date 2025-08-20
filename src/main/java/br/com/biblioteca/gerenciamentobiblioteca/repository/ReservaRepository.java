package br.com.biblioteca.gerenciamentobiblioteca.repository;

import br.com.biblioteca.gerenciamentobiblioteca.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}