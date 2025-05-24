package com.ceasbank.bankbackend.service;

import com.ceasbank.bankbackend.persistence.Account;
import com.ceasbank.bankbackend.persistence.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private static final String ACCOUNT_NOT_FOUND = "Account not found";
    private final AccountRepository accountRepository;

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public String depunere(Long id, double suma){
        Optional<Account> account = Optional.of(accountRepository.findById(id).orElseThrow(() -> new RuntimeException(ACCOUNT_NOT_FOUND) ));
        account.get().setBalance(account.get().getBalance() + suma);
        accountRepository.save(account.get());

        return "Depunere reusita";
    }

    public String retragere(Long id, double suma){
        Optional<Account> account = Optional.of(accountRepository.findById(id).orElseThrow(() -> new RuntimeException(ACCOUNT_NOT_FOUND) ));

        if(account.get().getBalance() < suma){
            throw new RuntimeException("Fonduri insuficiente");
        }
        account.get().setBalance(account.get().getBalance()-suma);
        accountRepository.save(account.get());

        return "Retragere reusita";
    }


    public String transfer(Long senderAccId, Long receiverAccId, double suma){
        Optional<Account> senderAcc = Optional.of(accountRepository.findById(senderAccId).orElseThrow(() -> new RuntimeException(ACCOUNT_NOT_FOUND) ));
        Optional<Account> receiverAcc = Optional.of(accountRepository.findById(receiverAccId).orElseThrow(() -> new RuntimeException(ACCOUNT_NOT_FOUND) ));

        if(senderAcc.get().getBalance() < suma){
            throw new RuntimeException("Fonduri insuficiente");
        }

        senderAcc.get().setBalance(senderAcc.get().getBalance()-suma);
        accountRepository.save(senderAcc.get());

        receiverAcc.get().setBalance(receiverAcc.get().getBalance()+suma);
        accountRepository.save(receiverAcc.get());

        return "Transfer reusit";
    }
}
