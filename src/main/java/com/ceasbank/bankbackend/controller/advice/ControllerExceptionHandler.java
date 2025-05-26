package com.ceasbank.bankbackend.controller.advice;

import com.ceasbank.bankbackend.exception.AccountNotFoundException;
import com.ceasbank.bankbackend.exception.ClientAlreadyExistsException;
import com.ceasbank.bankbackend.exception.ClientNotFoundException;
import com.ceasbank.bankbackend.exception.InsufficientBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Clasa care gestioneaza exceptiile globale pentru controllerele din aplicatie
 */

@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Gestioneaza exceptia {@link InsufficientBalanceException}
     *
     * Returneaza codul de stare 400 (Bad Request) cu mesajul exceptiei.
     * @param ex exceptia aruncata
     * @return raspuns HTTP cu mesajul de eroare
     */
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalance(InsufficientBalanceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Gestioneaza exceptia {@link AccountNotFoundException}
     *
     * @param ex exceptia aruncata
     * @return raspuns HTTP cu mesajul de eroare
     */
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFound(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Gestioneaza exceptia {@link ClientAlreadyExistsException}
     *
     * @param ex exceptia aruncata
     * @return raspuns HTTP cu mesajul de eroare
     */

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<String> clientAlreadyExists(ClientAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Gestioneaza exceptia {@link ClientNotFoundException}
     *
     * @param ex exceptia aruncata
     * @return raspuns HTTP cu mesajul de eroare
     */
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> clientNotFound(ClientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
