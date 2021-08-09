package com.everis.currentaccountservice.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.beans.ConstructorProperties;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCurrentAccount {

    private String message;
    private HttpStatus status;
    private Map<String, Object> body;

}
