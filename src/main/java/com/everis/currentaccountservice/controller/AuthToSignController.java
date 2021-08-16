package com.everis.currentaccountservice.controller;

import com.everis.currentaccountservice.model.Customer;
import com.everis.currentaccountservice.service.AuthToSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/c-accounts/c-account")
public class AuthToSignController {

    @Autowired
    private AuthToSignService service;

    @PostMapping("/auth-to-sign")
    public Mono<String> saveAuthToSignCustomer(@Valid @RequestBody Customer customer,
                                               @RequestParam String accountNumber) {
        return service.save(customer, accountNumber)
                .switchIfEmpty(Mono.empty());
    }
}
