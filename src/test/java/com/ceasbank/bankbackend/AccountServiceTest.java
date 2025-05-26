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

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByAccountId_shouldReturnAccount() {
        Account acc = Account.builder().id(1L).balance(100.0).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));

        Account result = accountService.findByAccountId(1L);

        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals(100.0, result.getBalance());
    }

    @Test
    void findByAccountId_shouldThrowIfNotFound() {
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.findByAccountId(99L));
    }

    @Test
    void getByClientId_shouldReturnAccount() {
        Account acc = Account.builder().id(2L).balance(200.0).build();
        when(accountRepository.findByClientId(5L)).thenReturn(Optional.of(acc));

        Account result = accountService.getByClientId(5L);

        Assertions.assertEquals(2L, result.getId());
        Assertions.assertEquals(200.0, result.getBalance());
    }

    @Test
    void getByClientId_shouldThrowIfNotFound() {
        when(accountRepository.findByClientId(999L)).thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.getByClientId(999L));
    }

    @Test
    void accountOperation_shouldDepositSuccessfully() {
        Account acc = Account.builder().id(1L).balance(100.0).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));

        Account result = accountService.accountOperation(1L, 50.0);

        Assertions.assertEquals(150.0, result.getBalance());
        verify(accountRepository).save(acc);
    }

    @Test
    void accountOperation_shouldWithdrawSuccessfully() {
        Account acc = Account.builder().id(1L).balance(100.0).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));

        Account result = accountService.accountOperation(1L, -40.0);

        Assertions.assertEquals(60.0, result.getBalance());
        verify(accountRepository).save(acc);
    }

    @Test
    void accountOperation_shouldThrowIfInsufficientBalance() {
        Account acc = Account.builder().id(1L).balance(20.0).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));

        Assertions.assertThrows(InsufficientBalanceException.class, () -> accountService.accountOperation(1L, -50.0));
    }

    @Test
    void transfer_shouldWorkCorrectly() {
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
        Account sender = Account.builder().id(1L).balance(10.0).build();
        Account receiver = Account.builder().id(2L).balance(50.0).build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(receiver));

        Assertions.assertThrows(InsufficientBalanceException.class, () -> accountService.transfer(1L, 2L, 50.0));
    }

    @Test
    void transfer_shouldThrowIfAnyAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.transfer(1L, 2L, 10.0));
    }
}
