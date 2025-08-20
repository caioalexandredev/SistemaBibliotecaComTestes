package br.com.biblioteca.gerenciamentobiblioteca.repository;

import br.com.biblioteca.gerenciamentobiblioteca.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}