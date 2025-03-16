package br.com.fiap.bank.DTOs;

public record TransactionsDTO(
        Integer accountId,
        Double amount
) {
}