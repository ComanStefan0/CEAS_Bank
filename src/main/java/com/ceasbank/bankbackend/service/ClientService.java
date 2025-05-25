package com.ceasbank.bankbackend.service;

import com.ceasbank.bankbackend.exception.AccountNotFoundException;
import com.ceasbank.bankbackend.exception.ClientNotFoundException;
import com.ceasbank.bankbackend.persistence.Account;
import com.ceasbank.bankbackend.persistence.AccountRepository;
import com.ceasbank.bankbackend.persistence.Client;
import com.ceasbank.bankbackend.persistence.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    public static final String CLIENT_NOT_FOUND = "Client with ID %d not found";

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    public Client saveClient(Client client) {
        Client savedClient = clientRepository.save(client);

        Account account = Account.builder()
                .balance(0)
                .client(savedClient)
                .build();

        accountRepository.save(account);

        savedClient.setAccount(account);

        return savedClient;
    }

    public Client getClientById(Long clientId) {
        Optional<Client> client = Optional.of(
                clientRepository.findById(clientId)
                        .orElseThrow(() -> new ClientNotFoundException(
                                String.format(CLIENT_NOT_FOUND, clientId))));
        return client.get();
    }

    @Transactional
    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

}
