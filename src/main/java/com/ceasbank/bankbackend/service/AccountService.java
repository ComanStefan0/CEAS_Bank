package com.ceasbank.bankbackend.service;

import com.ceasbank.bankbackend.exception.AccountNotFoundException;
import com.ceasbank.bankbackend.exception.InsufficientBalanceException;
import com.ceasbank.bankbackend.persistence.Account;
import com.ceasbank.bankbackend.persistence.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Clasa care se ocupa cu logica pentru conturile bancare.
 */
@Service
@AllArgsConstructor
public class AccountService {

    private static final String ACCOUNT_NOT_FOUND = "Account with id %d not found";
    private static final String INSUFFICIENT_FUNDS = "Insufficient funds on account ID %d for operation of %.2f.";
    private final AccountRepository accountRepository;

    /**
     * Cauta un cont dupa ID-ul contului.
     *
     * @param accountId ID-ul contului
     * @return contul gasit
     * @throws AccountNotFoundException daca nu exista un cont cu acest ID
     */
    public Account findByAccountId(Long accountId) {
        Optional<Account> account = Optional.of(
                accountRepository.findById(accountId)
                        .orElseThrow(() -> new AccountNotFoundException(
                                String.format(ACCOUNT_NOT_FOUND, accountId))));
        return account.get();
    }

    /**
     * Cauta un cont dupa ID-ul clientului.
     *
     * @param clientId asociat clientului
     * @return contul asociat clientului
     * @throws AccountNotFoundException daca nu exista un cont pentru acel client
     */
    public Account getByClientId(Long clientId) {
        Optional<Account> account = Optional.of(
                accountRepository.findByClientId(clientId)
                        .orElseThrow(() -> new AccountNotFoundException(
                                String.format(ACCOUNT_NOT_FOUND, clientId))));
        return account.get();
    }

    /**
     * Se efectueaaza o operatiune pe cont (depunere/retragere)
     *
     * @param accountId id-ul contului
     * @param suma      care se adauga (pe plus) sau se retrage (pe minus)
     * @return contul actualizat
     * @throws InsufficientBalanceException daca nu sunt bani suficienti pentru retragere
     */
    public Account accountOperation(Long accountId, double suma) {
        Account account = findByAccountId(accountId);
        if (account.getBalance() + suma < 0) {
            throw new InsufficientBalanceException(
                    String.format(INSUFFICIENT_FUNDS, accountId, suma));
        }
        account.setBalance(account.getBalance() + suma);
        accountRepository.save(account);

        return account;
    }

    /**
     * Se transfera o suma de bani intre doua conturi
     *
     * @param senderAccId   id-ul contului expeditor
     * @param receiverAccId id-ul contului destinatar
     * @param suma          care se transfera
     * @return contul expeditor actualizat
     * @throws InsufficientBalanceException daca expeditorul nu are bani suficienti
     */
    public Account transfer(Long senderAccId, Long receiverAccId, double suma) {
        Account senderAcc = findByAccountId(senderAccId);
        Account receiverAcc = findByAccountId(receiverAccId);


        if (senderAcc.getBalance() < suma) {
            throw new InsufficientBalanceException(
                    String.format(INSUFFICIENT_FUNDS, senderAccId, suma));
        }

        senderAcc.setBalance(senderAcc.getBalance() - suma);
        accountRepository.save(senderAcc);

        receiverAcc.setBalance(receiverAcc.getBalance() + suma);
        accountRepository.save(receiverAcc);

        return senderAcc;
    }
}
