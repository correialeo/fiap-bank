package br.com.fiap.bank.DTOs;

public record WithdrawDepositDTO(
        Integer accountId,
        Double amount
) {
}