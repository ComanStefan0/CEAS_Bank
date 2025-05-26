package com.ceasbank.bankbackend.exception;

/**
 * Exceptie aruncata atunci cand un client nu se afla in sistem.
 * Este utilizata pentru a semnala ca un client cautat prin ID sau alte criterii nu exista in baza de date
 */

public class ClientNotFoundException extends RuntimeException {

    /**
     * Creeaza o noua exceptie cu un mesaj personalizat
     *
     * @param message mesajul care descrie eroarea
     */
    public ClientNotFoundException(String message) {
        super(message);
    }
}
