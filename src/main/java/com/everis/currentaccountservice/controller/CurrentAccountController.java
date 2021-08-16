package com.everis.currentaccountservice.controller;

import com.everis.currentaccountservice.bean.ResponseCurrentAccount;
import com.everis.currentaccountservice.model.CurrentAccount;
import com.everis.currentaccountservice.model.Customer;
import com.everis.currentaccountservice.service.CurrentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/c-accounts")
public class CurrentAccountController {

    @Autowired
    private CurrentAccountService service;

    @PostMapping()
    public Mono<CurrentAccount> createAccount(@Valid @RequestBody Customer customer) {
        return service.create(customer);
    }

    @GetMapping("/c-account")
    public Mono<ResponseEntity<CurrentAccount>> getAccountById(@RequestParam String account) {
        return service.getByAccountNumber(account);
    }

    @GetMapping
    public Flux<CurrentAccount> getAllAccounts(){
        return service.findAll();
    }

    @DeleteMapping("/c-account")
    public Mono<ResponseEntity<CurrentAccount>> disableAccount(@RequestParam String account) {
        return service.disable(account)
                .flatMap( a -> Mono.just(ResponseEntity.ok(a)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/c-account")
    public Mono<ResponseEntity<CurrentAccount>> updateAccount(@RequestBody CurrentAccount account) {
        return service.update(account)
                .flatMap(a -> Mono.just(ResponseEntity.ok(a)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
