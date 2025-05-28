package com.ceasbank.bankbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Punctul de intrare principal al aplicatiei CEAS Bank
 * <p>
 * Aceasta clasa porneste aplicatia Spring Boot si activeaza suportul
 * pentru executia sarcinilor programate prin ({@code @Enable Scheduling}).
 */
@SpringBootApplication
@EnableScheduling  // Activează suportul pentru programarea sarcinilor
public class CeasBankApplication {
    /**
     * Metoda principala care porneste aplicatia Spring Boot
     *
     * @param args argumente transmise in linia de comanda (daca e nevoie)
     */
    public static void main(String[] args) {
        SpringApplication.run(CeasBankApplication.class, args);
    }
}