package com.everis.currentaccountservice.repository;

import com.everis.currentaccountservice.model.AuthToSign;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AuthToSignRepository extends ReactiveMongoRepository<AuthToSign, String> {

    Mono<AuthToSign> findByAccountNumberAndRuc(String accountNumber, String ruc);
}
