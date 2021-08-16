package com.everis.currentaccountservice.service;

import com.everis.currentaccountservice.bean.ResponseCurrentAccount;
import com.everis.currentaccountservice.model.CurrentAccount;
import com.everis.currentaccountservice.model.Customer;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrentAccountService {

    Flux<CurrentAccount> findAll();

    Mono<ResponseEntity<CurrentAccount>> getByAccountNumber(String accountNumber);

    Mono<CurrentAccount> create(Customer customer) ;

    Mono<CurrentAccount> update( CurrentAccount account);

     Mono<CurrentAccount> disable(String accountNumber);
}
