package com.everis.currentaccountservice.service.impl;

import com.everis.currentaccountservice.model.AuthToSign;
import com.everis.currentaccountservice.model.Customer;
import com.everis.currentaccountservice.repository.AuthToSignRepository;
import com.everis.currentaccountservice.service.AuthToSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.BiFunction;

@Service
public class AuthToSignServiceImpl implements AuthToSignService {

    @Autowired
    private AuthToSignRepository repository;

    @Override
    public Mono<String> save(Customer customer, String accountNumber) {
        return repository.findByAccountNumberAndRuc(accountNumber, customer.getRuc())
                .map( authToSignList -> addCustomerToAuthSignList(customer, authToSignList))
                .switchIfEmpty(saveNewAuthToSignList.apply(customer, accountNumber) );
    }

    @Override
    public Mono<AuthToSign> findByAccountNumberAndRuc(String accountNumber, String ruc) {
        return null;
    }

    private final BiFunction<Customer, String, Mono<String>> saveNewAuthToSignList = (customer, accountNumber) ->
        repository.save(new AuthToSign(
            accountNumber,
            customer.getRuc(),
            List.of(customer))                        )
            .thenReturn("Lista de firmantes autorizados creada");

    private String addCustomerToAuthSignList(Customer customer, AuthToSign authToSignList) {
        authToSignList.getAuthToSignCustomers().add(customer);
        repository.save(authToSignList).subscribe();
        return "Cliente autorizado para firmar agregado";
    }

}
