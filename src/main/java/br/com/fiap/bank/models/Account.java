package br.com.fiap.bank.models;

import br.com.fiap.bank.utils.EAccountType;

import java.time.LocalDate;
import java.util.Random;

public class Account {
    private Integer accountId;
    // 12345678-9
    private String accountNumber;
    //1234-5
    private String agency;
    private String holderName;
    private String holderCpf;
    private Double balance;
    private LocalDate openingDate;
    private Boolean isActive;
    private EAccountType accountType;

    public Account(Integer accountId, String accountNumber, String agency, String holderName, String holderCpf, Double balance,  LocalDate openingDate, Boolean isActive, EAccountType accountType) {
        this.accountId = Math.abs(new Random().nextInt());
        this.accountNumber = accountNumber;
        this.agency = agency;
        this.holderName = holderName;
        this.holderCpf = holderCpf;
        this.balance = balance;
        this.openingDate = LocalDate.now();
        this.isActive = isActive;
        this.accountType = accountType;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAgency() {
        return agency;
    }

    public String getHolderName() {
        return holderName;
    }

    public String getHolderCpf() {
        return holderCpf;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public EAccountType getAccountType() {
        return accountType;
    }
}
