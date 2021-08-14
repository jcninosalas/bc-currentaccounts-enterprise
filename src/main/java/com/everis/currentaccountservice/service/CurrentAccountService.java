package com.everis.currentaccountservice.service;

import com.everis.currentaccountservice.bean.ResponseCurrentAccount;
import com.everis.currentaccountservice.model.CurrentAccount;
import com.everis.currentaccountservice.model.EnterpriseAccounts;
import com.everis.currentaccountservice.model.EnterpriseCustomer;
import com.everis.currentaccountservice.repository.CurrentAccountRepository;
import com.everis.currentaccountservice.repository.EnterpriseAccountsRepository;
import com.everis.currentaccountservice.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class CurrentAccountService {

    @Autowired
    private CurrentAccountRepository repository;

    @Autowired
    private EnterpriseAccountsRepository eAccountsRepository;

    @Autowired
    private EnterpriseCustomerExternalService ecExternalService;

    public Flux<CurrentAccount> findAll() {
        return repository.findAll();
    }

    public Mono<ResponseEntity<CurrentAccount>> getByAccountNumber(String accountNumber)  {
        return repository.findByAccountNumber(accountNumber)
                .map(a -> ResponseEntity.ok().body(a))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Mono<ResponseCurrentAccount> create(String ruc) {
        return ecExternalService.getEnterpriseCustomer(ruc)
                .flatMap( c -> addNewAccountToCustomer(ruc))
                .switchIfEmpty(
                        addNewAccountToNewCustomer(ruc)
                );
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

    private CurrentAccount createNewCurrentAccount() {
        CurrentAccount account = new CurrentAccount();
        account.setAccountNumber(Utils.generateAccountNumber());
        account.setCreatedAt(new Date());
        account.setAccountBalance(new BigDecimal(0));
        account.setIsActive(true);
        account.setMaintenanceFee(3);
        return account;
    }

    private Mono<? extends ResponseCurrentAccount> addNewAccountToNewCustomer(String ruc) {
        CurrentAccount newAccount = createNewCurrentAccount();
        ResponseCurrentAccount response = new ResponseCurrentAccount();
        Map<String, Object> responseBody = new HashMap<>();
        EnterpriseAccounts enterpriseAccounts = new EnterpriseAccounts();
        ArrayList<CurrentAccount> currentAccounts = new ArrayList<>();
        return repository.save(newAccount)
                .flatMap( c -> {
                    currentAccounts.add(newAccount);
                    enterpriseAccounts.setRuc(ruc);
                    enterpriseAccounts.setAccounts(currentAccounts);
                    createNewResponse(c, response, responseBody);
                    eAccountsRepository.save(enterpriseAccounts).subscribe();
                    return Mono.just(response);
                });
    }

    private Mono<ResponseCurrentAccount> addNewAccountToCustomer(String ruc) {
        CurrentAccount newAccount = createNewCurrentAccount();
        ResponseCurrentAccount response = new ResponseCurrentAccount();
        Map<String, Object> responseBody = new HashMap<>();
        return eAccountsRepository.findByRuc(ruc)
                .flatMap( c -> {
                    repository.save(newAccount).subscribe();
                    saveNewEnterpriseAccount(ruc, newAccount, c);
                    createNewResponse(newAccount, response, responseBody);
                    return Mono.just(response);
                });
    }

    private void saveNewEnterpriseAccount(String ruc, CurrentAccount newAccount, EnterpriseAccounts c) {
        ArrayList<CurrentAccount> accounts = c.getAccounts();
        accounts.add(newAccount);
        c.setAccounts(accounts);
        c.setRuc(ruc);
        eAccountsRepository.save(c).subscribe();
    }

    private void createNewResponse(CurrentAccount newAccount, ResponseCurrentAccount response, Map<String, Object> responseBody) {
        response.setMessage("Cuenta registrada con exito");
        response.setStatus(HttpStatus.OK);
        responseBody.put("account:", newAccount);
        response.setBody(responseBody);
    }
}
