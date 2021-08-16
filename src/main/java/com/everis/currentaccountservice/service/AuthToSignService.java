package com.everis.currentaccountservice.service;

import com.everis.currentaccountservice.bean.ResponseCurrentAccount;
import com.everis.currentaccountservice.model.AuthToSign;
import com.everis.currentaccountservice.model.Customer;
import reactor.core.publisher.Mono;

public interface AuthToSignService {

    Mono<String> save(Customer customer, String accountNumber);

    Mono<AuthToSign> findByAccountNumberAndRuc(String accountNumber, String ruc);
}
