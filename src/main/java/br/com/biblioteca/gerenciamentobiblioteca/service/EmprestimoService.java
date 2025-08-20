package br.com.biblioteca.gerenciamentobiblioteca.service;

import br.com.biblioteca.gerenciamentobiblioteca.model.Emprestimo;
import br.com.biblioteca.gerenciamentobiblioteca.repository.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    private static final int MAX_RENOVACOES = 2;
    private static final int DIAS_ADICIONAIS_RENOVACAO = 14;

    @Transactional
    public Emprestimo renovarEmprestimo(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado."));

        // Verificar data de vencimento
        if (emprestimo.getDataVencimento().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Não é possível renovar um empréstimo vencido.");
        }

        // Verificar limite de renovações
        if (emprestimo.getNumeroRenovacoes() >= MAX_RENOVACOES) {
            throw new IllegalStateException("Limite máximo de renovações atingido.");
        }

        // Efetivar a renovação
        emprestimo.setNumeroRenovacoes(emprestimo.getNumeroRenovacoes() + 1);
        emprestimo.setDataVencimento(emprestimo.getDataVencimento().plusDays(DIAS_ADICIONAIS_RENOVACAO));

        return emprestimoRepository.save(emprestimo);
    }
}