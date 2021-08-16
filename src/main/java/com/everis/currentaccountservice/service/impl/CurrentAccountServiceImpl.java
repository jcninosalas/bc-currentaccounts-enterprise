package com.everis.currentaccountservice.service.impl;

import com.everis.currentaccountservice.bean.ResponseCurrentAccount;
import com.everis.currentaccountservice.model.CurrentAccount;
import com.everis.currentaccountservice.model.Customer;
import com.everis.currentaccountservice.model.EnterpriseAccounts;
import com.everis.currentaccountservice.repository.CurrentAccountRepository;
import com.everis.currentaccountservice.repository.EnterpriseAccountsRepository;
import com.everis.currentaccountservice.service.CurrentAccountService;
import com.everis.currentaccountservice.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.BiFunction;

@Slf4j
@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {

    @Autowired
    private CurrentAccountRepository repository;

    @Autowired
    private EnterpriseAccountsRepository eAccountsRepository;

    @Override
    public Flux<CurrentAccount> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<ResponseEntity<CurrentAccount>> getByAccountNumber(String accountNumber)  {
        return repository.findByAccountNumber(accountNumber)
                .map(a -> ResponseEntity.ok().body(a))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<CurrentAccount> create(Customer customer) {
        return repository.save(Utils.createNewCurrentAccount(customer))
                .flatMap(account -> addNewAccountToCustomer.apply(customer.getRuc(), account));
    }

    @Override
    public Mono<CurrentAccount> update( CurrentAccount account) {
       return repository.findByAccountNumber(account.getAccountNumber())
               .flatMap( a -> {
                   account.setId(a.getId());
                   return repository.save(account);
               })
               .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<CurrentAccount> disable(String accountNumber) {
        return repository.findByAccountNumber(accountNumber)
                .flatMap( a -> {
                    a.setIsActive(false);
                    return repository.save(a);
                })
                .switchIfEmpty(Mono.empty());
    }

    private final BiFunction<String, CurrentAccount, Mono<CurrentAccount>> addNewAccountToCustomer = (ruc, account) ->
            eAccountsRepository.findByRuc(ruc)
                .map( accounts -> addNewAccountToCustomerAccounts(account, accounts))
                .switchIfEmpty(createCustomerAccounts(ruc, account));

    private Mono<CurrentAccount> createCustomerAccounts(String ruc, CurrentAccount account) {
        var enterpriseAccounts = new EnterpriseAccounts();
        enterpriseAccounts.setAccounts(List.of(account.getAccountNumber()));
        enterpriseAccounts.setRuc(ruc);
        return eAccountsRepository.save(enterpriseAccounts)
                .map(c -> account);
    }

    private CurrentAccount addNewAccountToCustomerAccounts(CurrentAccount account, EnterpriseAccounts acounts) {
        acounts.getAccounts().add(account.getAccountNumber());
        eAccountsRepository.save(acounts).subscribe();
        return account;
    }

//    private final Function<EnterpriseAccounts, EnterpriseAccounts> addAccount = eAccounts -> {
//        eAccounts.getAccounts().add(Utils.createNewCurrentAccount());
//        return eAccounts;
//    };

//    private Mono<? extends ResponseCurrentAccount> addNewAccountToNewCustomer(String ruc) {
//        CurrentAccount newAccount = createNewCurrentAccount();
//        ResponseCurrentAccount response = new ResponseCurrentAccount();
//        Map<String, Object> responseBody = new HashMap<>();
//        EnterpriseAccounts enterpriseAccounts = new EnterpriseAccounts();
//        ArrayList<CurrentAccount> currentAccounts = new ArrayList<>();
//        return repository.save(newAccount)
//                .flatMap( c -> {
//                    currentAccounts.add(newAccount);
//                    enterpriseAccounts.setRuc(ruc);
//                    enterpriseAccounts.setAccounts(currentAccounts);
//                    createNewResponse(c, response, responseBody);
//                    eAccountsRepository.save(enterpriseAccounts).subscribe();
//                    return Mono.just(response);
//                });
//    }
//

//    private Mono<ResponseCurrentAccount> addNewAccountToCustomer(Customer customer) {
//        CurrentAccount newAccount = Utils.createNewCurrentAccount(customer);
//        ResponseCurrentAccount response = new ResponseCurrentAccount();
//        Map<String, Object> responseBody = new HashMap<>();
//        return eAccountsRepository.findByRuc(customer.getRuc())
//                .flatMap( c -> {
//                    repository.save(newAccount).subscribe();
//                    saveNewEnterpriseAccount(customer.getRuc(), newAccount, c);
//                    createNewResponse(newAccount, response, responseBody);
//                    return Mono.just(response);
//                });
//    }

//    private void saveNewEnterpriseAccount(String ruc, CurrentAccount newAccount, EnterpriseAccounts c) {
//        ArrayList<CurrentAccount> accounts = c.getAccounts();
//        accounts.add(newAccount);
//        c.setAccounts(accounts);
//        c.setRuc(ruc);
//        eAccountsRepository.save(c).subscribe();
//    }

//    private void createNewResponse(CurrentAccount newAccount, ResponseCurrentAccount response, Map<String, Object> responseBody) {
//        response.setMessage("Cuenta registrada con exito");
//        response.setStatus(HttpStatus.OK);
//        responseBody.put("account:", newAccount);
//        response.setBody(responseBody);
//    }
}
