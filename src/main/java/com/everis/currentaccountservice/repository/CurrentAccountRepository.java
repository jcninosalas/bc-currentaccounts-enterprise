package com.everis.currentaccountservice.repository;

import com.everis.currentaccountservice.model.CurrentAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface CurrentAccountRepository extends ReactiveMongoRepository<CurrentAccount, String> {

    Mono<CurrentAccount> findByAccountNumber(String account);



}
