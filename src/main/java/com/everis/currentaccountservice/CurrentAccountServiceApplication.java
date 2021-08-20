package com.everis.currentaccountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CurrentAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrentAccountServiceApplication.class, args);
	}

}
