package com.everis.currentaccountservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@ToString
@Getter
@Setter
public class EnterpriseCustomer {

    @Id
    private String id;
    private String companyName;
    private String contactFirstName;
    private String contactLastName;
    private String ruc;
}
