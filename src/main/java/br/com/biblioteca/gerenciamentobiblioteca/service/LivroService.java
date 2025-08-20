package br.com.biblioteca.gerenciamentobiblioteca.service;

import br.com.biblioteca.gerenciamentobiblioteca.model.Livro;
import br.com.biblioteca.gerenciamentobiblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> pesquisarLivros(String titulo, String autor, String categoria) {
        // Cria um "livro de exemplo" com os critérios de busca fornecidos
        Livro livroExemplo = new Livro();
        livroExemplo.setTitulo(titulo);
        livroExemplo.setAutor(autor);
        livroExemplo.setCategoria(categoria);

        // Cria um "Matcher" para definir COMO a busca deve ser feita
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase() // Ignora maiúsculas/minúsculas
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Busca por "contém" em vez de "exato"

        // Cria o Exemplo e executa a busca
        Example<Livro> example = Example.of(livroExemplo, matcher);

        return livroRepository.findAll(example);
    }
}