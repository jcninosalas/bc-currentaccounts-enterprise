package com.everis.currentaccountservice.controller;

import com.everis.currentaccountservice.model.CurrentAccount;
import com.everis.currentaccountservice.service.CurrentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CurrentAccountController {

    @Autowired
    private CurrentAccountService service;

    @PostMapping("/c-account/new")
    public Mono<CurrentAccount> createAccount() {
        return service.create();
    }

    @GetMapping("/c-account/find")
    public Mono<ResponseEntity<CurrentAccount>> getAccountById(@RequestParam String account) {
        return service.getByAccountNumber(account);
    }

    @GetMapping("/c-account")
    public Flux<CurrentAccount> getAllAccounts(){
        return service.findAll();
    }

    @PutMapping("/c-account/disable")
    public Mono<ResponseEntity<CurrentAccount>> disableAccount(@RequestParam String account) {
        return service.disable(account)
                .flatMap( a -> Mono.just(ResponseEntity.ok(a)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/c-account/update")
    public Mono<ResponseEntity<CurrentAccount>> updateAccount(@RequestBody CurrentAccount account) {
        return service.update(account)
                .flatMap(a -> Mono.just(ResponseEntity.ok(a)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
