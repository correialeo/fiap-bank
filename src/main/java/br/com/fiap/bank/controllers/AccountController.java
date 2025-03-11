package br.com.fiap.bank.controllers;

import br.com.fiap.bank.models.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {

    private List<Account> repository = new ArrayList<>();

    @GetMapping("/accounts")
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
        if(account.getInitialBalance() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Initial balance must be positive");
        }
        account.setActive(true);
        repository.add(account);
        return ResponseEntity.status(201).body(account);
    }
}
