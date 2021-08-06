package com.everis.currentaccountservice.service;

import com.everis.currentaccountservice.model.CurrentAccount;
import com.everis.currentaccountservice.repository.CurrentAccountRepository;
import com.everis.currentaccountservice.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;


@Service
public class CurrentAccountService {

    @Autowired
    private CurrentAccountRepository repository;

    public Flux<CurrentAccount> findAll() {
        return repository.findAll();
    }

    public Mono<ResponseEntity<CurrentAccount>> getByAccountNumber(String accountNumber)  {
        return repository.findByAccountNumber(accountNumber)
                .map(a -> ResponseEntity.ok().body(a))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Mono<CurrentAccount> create() {
        Mono<CurrentAccount> account = setNewCurrentAccount();
        return account.flatMap(repository::insert);
    }

    public Mono<CurrentAccount> update( CurrentAccount account) {
       return repository.findByAccountNumber(account.getAccountNumber())
               .flatMap( a -> {
                   account.setId(a.getId());
                   return repository.save(account);
               })
               .switchIfEmpty(Mono.empty());
    }

    public Mono<CurrentAccount> disable(String accountNumber) {
        return repository.findByAccountNumber(accountNumber)
                .flatMap( a -> {
                    a.setIsActive(false);
                    return repository.save(a);
                })
                .switchIfEmpty(Mono.empty());
    }

    private Mono<CurrentAccount> setNewCurrentAccount() {
        CurrentAccount account = new CurrentAccount();
        account.setAccountNumber(Utils.generateAccountNumber());
        account.setCreatedAt(new Date());
        account.setAccountBalance(new BigDecimal(0));
        account.setIsActive(true);
        account.setMaintenanceFee(3);
        return Mono.just(account);
    }

}
