package com.everis.currentaccountservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Setter
@Getter
@Document
public class EnterpriseAccounts {

    @Id
    private String id;
    private String ruc;
    private ArrayList<CurrentAccount> accounts;
}
