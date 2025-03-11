package br.com.fiap.bank.controllers;

import br.com.fiap.bank.models.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<Account> repository = new ArrayList<>();

    @GetMapping("/account")
    public ResponseEntity<List<Account>> getAllAccounts() {
        log.info("Retrieving all accounts");
        return ResponseEntity.ok(repository);
    }

    @PostMapping("/account")
    public ResponseEntity<Account> createAccount (@RequestBody Account account) {
        if(account.getHolderName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }
        if(account.getHolderCpf() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF is required");
        }
        if(account.getBalance() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Initial balance must be positive");
        }
        log.info("Creating account");
        account.setActive(true);
        repository.add(account);
        return ResponseEntity.status(201).body(account);
    }

    @GetMapping("/account/id/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable int accountId) {
        log.info("Retrieving account by id: " + accountId);
        return ResponseEntity.status(200).body(getAccount(accountId));
    }

    @GetMapping("/account/cpf/{cpf}")
    public ResponseEntity<Account> getAccountByCpf(@PathVariable String cpf) {
        log.info("Retrieving account by cpf: " + cpf);
        Optional<Account> accountEntity = repository.stream().
                filter(account -> account.getHolderCpf().equals(cpf)).
                findFirst();
        return accountEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("account/inactivate")
    public ResponseEntity<Account> inactivateAccount(@RequestParam int accountId) {
        log.info("Inactivating account");
        Account account = getAccount(accountId);
        account.setActive(false);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/account/deposit")
    public ResponseEntity<Account> deposit(@RequestParam int accountId, @RequestParam Double amount) {
        log.info("Deposit request");
        Account account = getAccount(accountId);
        account.setBalance(account.getBalance() + amount);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/account/withdraw")
    public ResponseEntity<Account> withdraw(@RequestParam int accountId, @RequestParam Double amount) {
        log.info("Withdraw request");
        Account account = getAccount(accountId);
        if(account.getBalance() >= amount){
            account.setBalance(account.getBalance() - amount);
            return ResponseEntity.ok(account);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }
    }

    @PutMapping("/account/pix")
    public ResponseEntity<Account> transferPix(@RequestParam int accountId, @RequestParam int pixAccountId, @RequestParam Double amount) {
        log.info("Transfering pix from account " + accountId + " to account " + pixAccountId);
        Account account = getAccount(accountId);
        if(account.getBalance() >= amount){
            Account pixAccount = getAccount(pixAccountId);
            account.setBalance(account.getBalance() - amount);
            pixAccount.setBalance(pixAccount.getBalance() + amount);
            return ResponseEntity.ok(account);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }
    }

    private Account getAccount(Integer id){
        return repository.stream()
                .filter(account -> account.getAccountId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }
}