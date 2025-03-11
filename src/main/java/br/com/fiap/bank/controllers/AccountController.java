package br.com.fiap.bank.controllers;

import br.com.fiap.bank.models.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {

    private List<Account> repository = new ArrayList<>();

    @GetMapping("/account")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(repository);
    }

    @PostMapping("/account")
    public ResponseEntity<Account> createAccount (Account account) {
        if(account.getHolderName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }
        if(account.getHolderCpf() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF is required");
        }
        if(account.getBalance() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Initial balance must be positive");
        }
        account.setActive(true);
        repository.add(account);
        return ResponseEntity.status(201).body(account);
    }

    @GetMapping("/account/id")
    public ResponseEntity<Account> getAccountById(@RequestParam int accountId) {

        Optional<Account> accountEntity = repository.stream().
                    filter(account -> account.getAccountId() == accountId).
                    findFirst();
        return accountEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/account/cpf")
    public ResponseEntity<Account> getAccountByCpf(@RequestParam String cpf) {

        Optional<Account> accountEntity = repository.stream().
                filter(account -> account.getHolderCpf().equals(cpf)).
                findFirst();
        return accountEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("account/inactivate")
    public ResponseEntity<Account> inactivateAccount(@RequestParam int accountId) {
        Optional<Account> accountEntity = repository.stream().
                filter(account -> account.getAccountId() == accountId).
                findFirst();

        if(accountEntity.isPresent()){
            Account account = accountEntity.get();
            account.setActive(false);
            return ResponseEntity.ok(account);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/account/deposit")
    public ResponseEntity<Account> deposit(@RequestParam int accountId, @RequestParam Double amount) {
        Optional<Account> accountEntity = repository.stream().
                filter(account -> account.getAccountId() == accountId).
                findFirst();

        if(accountEntity.isPresent()){
            Account account = accountEntity.get();
            account.setBalance(account.getBalance() + amount);
            return ResponseEntity.ok(account);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/account/withdraw")
    public ResponseEntity<Account> withdraw(@RequestParam int accountId, @RequestParam Double amount) {
        Optional<Account> accountEntity = repository.stream().
                filter(account -> account.getAccountId() == accountId).
                findFirst();

        if(accountEntity.isPresent()){
            Account account = accountEntity.get();
            if(account.getBalance() >= amount){
                account.setBalance(account.getBalance() - amount);
                return ResponseEntity.ok(account);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/account/pix")
    public ResponseEntity<Account> transferPix(@RequestParam int accountId, @RequestParam int pixAccountId, @RequestParam Double amount) {
        Optional<Account> accountEntity = repository.stream().
                filter(account -> account.getAccountId() == accountId).
                findFirst();

        if(accountEntity.isPresent()){
            Account account = accountEntity.get();
            if(account.getBalance() >= amount){
                Optional<Account> pixAccountEntity = repository.stream().
                        filter(account1 -> account1.getAccountId() == pixAccountId).
                        findFirst();
                if(pixAccountEntity.isPresent()){
                    Account pixAccount = pixAccountEntity.get();
                    account.setBalance(account.getBalance() - amount);
                    pixAccount.setBalance(pixAccount.getBalance() + amount);
                    return ResponseEntity.ok(account);
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PIX account not found");
                }
            }
        }

        return ResponseEntity.notFound().build();
    }
}
