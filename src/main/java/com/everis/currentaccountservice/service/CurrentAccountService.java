package com.everis.currentaccountservice.service;

import com.everis.currentaccountservice.model.CurrentAccount;
import com.everis.currentaccountservice.repository.CurrentAccountRepository;
import com.everis.currentaccountservice.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    //Revisar
    public Mono<CurrentAccount> update( CurrentAccount account) {
       return repository.findByAccountNumber(account.getAccountNumber())
               .map(a -> {account.setId(a.getId()); return a;})
               .flatMap(repository::save);
    }

    public Mono<CurrentAccount> disable(String accountNumber) {
        return repository.findByAccountNumber(accountNumber)
                //.switchIfEmpty(Mono.error(new Exception("Cuenta no registrada")))
                .map(a -> {a.setIsActive(false); return a;})
                .flatMap(repository::save);
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
