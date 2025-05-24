package com.ceasbank.bankbackend.service;

import com.ceasbank.bankbackend.persistence.Account;
import com.ceasbank.bankbackend.persistence.Client;
import com.ceasbank.bankbackend.persistence.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final AccountService accountService;

    public Client saveClient(Client client) {
        Client savedClient = clientRepository.save(client);
        Account account = Account.builder()
                .balance(0)
                .client(savedClient)
                .build();
        accountService.saveAccount(account);
        savedClient.setAccount(account);
        return savedClient;
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }
}
