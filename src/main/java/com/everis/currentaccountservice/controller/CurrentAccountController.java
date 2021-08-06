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
    public Mono<?> createAccount() {
        return service.create();
    }

//    @GetMapping("/c-account/find")
//    public Mono<?> getAccountById(@RequestParam String account) throws Exception {
//        return service.getByAccountNumber(account);
//    }

    @GetMapping("/c-account/find")
    public Mono<ResponseEntity<CurrentAccount>> getAccountById(@RequestParam String account) {
        return service.getByAccountNumber(account);
    }

    @GetMapping("/c-account")
    public Flux<CurrentAccount> getAllAccounts(){
        return service.findAll();
    }

    @PutMapping("/c-account/disable")
    public Mono<CurrentAccount> disableAccount(@RequestParam String account) {
        return service.disable(account);
    }

    @PutMapping("/c-account/update")
    public Mono<CurrentAccount> updateAccount(@RequestBody CurrentAccount account) {
        return service.update(account);
    }
}
