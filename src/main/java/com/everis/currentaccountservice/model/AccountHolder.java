package com.everis.currentaccountservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

//Lista de titulares de una cuenta
@Getter
@Setter
@AllArgsConstructor
@Document
public class AccountHolder {

    @Id
    private String id;
    private String accountNumber;
    private String ruc;
    private ArrayList<Customer> accountHolders;
}
