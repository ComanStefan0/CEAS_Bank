package com.ceasbank.bankbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // Activează suportul pentru programarea sarcinilor
public class CeasBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(CeasBankApplication.class, args);
	}
}