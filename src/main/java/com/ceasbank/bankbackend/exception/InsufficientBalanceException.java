package com.ceasbank.bankbackend.exception;

/**
 * Exceptie aruncata atunci cand un cont nu are destul sold pentru a termina o operatiunea.
 * <p>
 * Este folosita in operatiuni precum retrageri sau transferuri pentru a preveni depasirea soldului curent.
 */

public class InsufficientBalanceException extends RuntimeException {
    /**
     * Creeaza o noua exceptie cu un mesaj explicativ.
     *
     * @param message mesajul care descrie eroarea.
     */
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
