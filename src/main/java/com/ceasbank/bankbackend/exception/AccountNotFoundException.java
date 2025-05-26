package com.ceasbank.bankbackend.exception;

/**
 * Exceptie aruncata dupa ce contul bancar nu este gasit in sistem
 * Este utlizata pentru a atentiona clientul cu privire la situatiile in care
 * operatia ceruta implica un cont inexistent.
 */

public class AccountNotFoundException extends RuntimeException {
    /**
     * Creeaza o noua exceptie cu un mesaj personalizat
     *
     * @param message mesajul care descrie eroarea
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
}
