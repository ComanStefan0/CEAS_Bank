package com.ceasbank.bankbackend;

import com.ceasbank.bankbackend.exception.AccountNotFoundException;
import com.ceasbank.bankbackend.exception.InsufficientBalanceException;
import com.ceasbank.bankbackend.persistence.Account;
import com.ceasbank.bankbackend.persistence.AccountRepository;
import com.ceasbank.bankbackend.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Teste unitare pentru clasa {@link AccountService}
 * <p>
 * Se foloseste Mockito pentru a simula comportamenul repository-ului
 * si JUnit pentru a verifica logica de business a operatiilor pe conturi:
 * - cautare cont,
 * - operatii de depunere/retragere
 * - transferuri intre conturi
 * - si tratarea exceptiilor
 */
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    /**
     * Creeaza mock-urile inainte de fiecare test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByAccountId_shouldReturnAccount() {
        /** Verifica daca metoda returneaza contul corect dupa ID
         */
        Account acc = Account.builder().id(1L).balance(100.0).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));

        Account result = accountService.findByAccountId(1L);

        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals(100.0, result.getBalance());
    }

    @Test
    void findByAccountId_shouldThrowIfNotFound() {
        /** Verifica daca se arunca exceptia cand contul nu exista.
         */
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.findByAccountId(99L));
    }

    @Test
    void getByClientId_shouldReturnAccount() {
        /**
         * Verifica daca se arunca exceptia cand contul nu exista
         */

        Account acc = Account.builder().id(2L).balance(200.0).build();
        when(accountRepository.findByClientId(5L)).thenReturn(Optional.of(acc));

        Account result = accountService.getByClientId(5L);

        Assertions.assertEquals(2L, result.getId());
        Assertions.assertEquals(200.0, result.getBalance());
    }

    @Test
    void getByClientId_shouldThrowIfNotFound() {
        /** Verifica returnarea contului dupa ID-ul clientului
         */
        when(accountRepository.findByClientId(999L)).thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.getByClientId(999L));
    }

    @Test
    void accountOperation_shouldDepositSuccessfully() {
        /** Verifica pentru depunere reusita in cont
         */
        Account acc = Account.builder().id(1L).balance(100.0).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));

        Account result = accountService.accountOperation(1L, 50.0);

        Assertions.assertEquals(150.0, result.getBalance());
        verify(accountRepository).save(acc);
    }

    @Test
    void accountOperation_shouldWithdrawSuccessfully() {
        /** Test pentru retragere reusita in cont
         */
        Account acc = Account.builder().id(1L).balance(100.0).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));

        Account result = accountService.accountOperation(1L, -40.0);

        Assertions.assertEquals(60.0, result.getBalance());
        verify(accountRepository).save(acc);
    }

    @Test
    void accountOperation_shouldThrowIfInsufficientBalance() {
        /** Verifica daca retragerea peste sold arunca exceptia
         */
        Account acc = Account.builder().id(1L).balance(20.0).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));

        Assertions.assertThrows(InsufficientBalanceException.class, () -> accountService.accountOperation(1L, -50.0));
    }

    @Test
    void transfer_shouldWorkCorrectly() {
        /** Test pentru transfer reusit intre conturi
         */
        Account sender = Account.builder().id(1L).balance(100.0).build();
        Account receiver = Account.builder().id(2L).balance(50.0).build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(receiver));

        Account result = accountService.transfer(1L, 2L, 30.0);

        Assertions.assertEquals(70.0, sender.getBalance());
        Assertions.assertEquals(80.0, receiver.getBalance());

        verify(accountRepository).save(sender);
        verify(accountRepository).save(receiver);
    }

    @Test
    void transfer_shouldThrowIfSenderHasInsufficientFunds() {
        /**
         * Verifica daca se arunca exceptie cand expeditorul nu are bani suficienti
         */

        Account sender = Account.builder().id(1L).balance(10.0).build();
        Account receiver = Account.builder().id(2L).balance(50.0).build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(receiver));

        Assertions.assertThrows(InsufficientBalanceException.class, () -> accountService.transfer(1L, 2L, 50.0));
    }

    @Test
    void transfer_shouldThrowIfAnyAccountNotFound() {
        /**
         *  Verifica daca se arunca exceptie cand un cont implicat nu exista
         */
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.transfer(1L, 2L, 10.0));
    }
}
