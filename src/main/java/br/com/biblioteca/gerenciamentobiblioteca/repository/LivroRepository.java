package br.com.biblioteca.gerenciamentobiblioteca.repository;

import br.com.biblioteca.gerenciamentobiblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>, QueryByExampleExecutor<Livro> {

}