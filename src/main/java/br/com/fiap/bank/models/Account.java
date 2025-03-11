package br.com.fiap.bank.models;

import br.com.fiap.bank.utils.EAccountType;

import java.time.LocalDate;

public class Account {
    // 12345678-9
    private String accountNumber;
    //1234-5
    private String agency;
    private String holderName;
    private String holderCpf;
    private LocalDate openingDate;
    private Boolean isActive;
    private EAccountType accountType;

    public Account(String accountNumber, String agency, String holderName, String holderCpf, LocalDate openingDate, Boolean isActive, EAccountType accountType) {
        this.accountNumber = accountNumber;
        this.agency = agency;
        this.holderName = holderName;
        this.holderCpf = holderCpf;
        this.openingDate = openingDate;
        this.isActive = isActive;
        this.accountType = accountType;
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
