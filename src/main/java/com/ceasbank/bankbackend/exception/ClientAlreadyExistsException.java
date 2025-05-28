package com.ceasbank.bankbackend.exception;

/**
 * Exceptie aruncata atunci cand se incearca inregistrarea unui client care exista deja in sistem
 * <p>
 * Este folosita pentru a preveni duplicatele de utilizatori in baza de date.
 */
public class ClientAlreadyExistsException extends RuntimeException {
    /**
     * Creeaza o noua exceptie cu un mesaj personalizat
     *
     * @param message mesajul care descrie eroarea
     */
    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}
