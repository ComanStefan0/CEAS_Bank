package com.ceasbank.bankbackend.service;

import com.ceasbank.bankbackend.dto.ClientRequestDTO;
import com.ceasbank.bankbackend.dto.ClientResponseDTO;
import com.ceasbank.bankbackend.exception.ClientAlreadyExistsException;
import com.ceasbank.bankbackend.exception.ClientNotFoundException;
import com.ceasbank.bankbackend.persistence.Account;
import com.ceasbank.bankbackend.persistence.AccountRepository;
import com.ceasbank.bankbackend.persistence.Client;
import com.ceasbank.bankbackend.persistence.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    public static final String CLIENT_NOT_FOUND = "Client with ID %d not found";
    public static final String CLIENT_ALREADY_EXISTING = "Client with username %s already exists";

    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    public ClientResponseDTO saveClient(ClientRequestDTO clientRequestDTO) {
        Optional <Client> existingClient = clientRepository.findByUsername(clientRequestDTO.getUsername());
        if (existingClient.isPresent()) {
            throw new ClientAlreadyExistsException(String.format(CLIENT_ALREADY_EXISTING, clientRequestDTO.getUsername()));
        }

        Client client = Client.builder()
                .nume(clientRequestDTO.getNume())
                .prenume(clientRequestDTO.getPrenume())
                .cnp(clientRequestDTO.getCnp())
                .username(clientRequestDTO.getUsername())
                .password(passwordEncoder.encode(clientRequestDTO.getPassword()))
                .build();

        Client savedClient = clientRepository.save(client);

        Account account = Account.builder()
                .balance(0)
                .client(savedClient)
                .build();

        accountRepository.save(account);

        savedClient.setAccount(account);
        return ClientResponseDTO.builder()
                .cnp(savedClient.getCnp())
                .nume(savedClient.getNume())
                .prenume(savedClient.getPrenume())
                .build();
    }

    public ClientResponseDTO getClientById(Long clientId) {
        Optional<Client> optionalClient = Optional.of(
                clientRepository.findById(clientId)
                        .orElseThrow(() -> new ClientNotFoundException(
                                String.format(CLIENT_NOT_FOUND, clientId))));
        Client client = optionalClient.get();
        return ClientResponseDTO.builder()
                .cnp(client.getCnp())
                .nume(client.getNume())
                .prenume(client.getPrenume())
                .build();
    }

    @Transactional
    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

}
