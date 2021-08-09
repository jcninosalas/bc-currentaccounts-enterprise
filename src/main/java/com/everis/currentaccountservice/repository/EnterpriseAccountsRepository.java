package com.everis.currentaccountservice.repository;

import com.everis.currentaccountservice.model.EnterpriseAccounts;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface EnterpriseAccountsRepository extends ReactiveMongoRepository<EnterpriseAccounts, String> {

    Mono<EnterpriseAccounts> findByRuc(String ruc);
}
