package com.everis.currentaccountservice.service;

import com.everis.currentaccountservice.model.EnterpriseCustomer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EnterpriseCustomerExternalService {

    private final String SERVICE_URL = "http://localhost:8090/";
    private final WebClient webClient = WebClient.create(SERVICE_URL);

    public Mono<EnterpriseCustomer> getEnterpriseCustomer(String ruc) {
        //TODO utilizar el config-server
        String ENTERPRISE_CONSUMER_ENDPOINT = "e-customer//e-customer?ruc=";
        return webClient.get()
                .uri(ENTERPRISE_CONSUMER_ENDPOINT.concat(ruc))
                .exchangeToMono(response ->
                        response.bodyToMono(EnterpriseCustomer.class));
    }
}
