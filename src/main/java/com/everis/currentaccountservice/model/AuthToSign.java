package com.everis.currentaccountservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

//Lista que firmantes autorizados
@Getter
@Setter
@Document
public class AuthToSign {

    @Id
    private String id;
    private String accountNumber;
    private String ruc;
    private List<Customer> authToSignCustomers;

    public AuthToSign(String accountNumber, String ruc, List<Customer> authToSignCustomers) {
        this.accountNumber = accountNumber;
        this.ruc = ruc;
        this.authToSignCustomers = authToSignCustomers;
    }
}
