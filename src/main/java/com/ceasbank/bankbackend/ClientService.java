package com.ceasbank.bankbackend;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public String depunere(String id, double suma){
        Optional<Client> client = Optional.of(clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found") ));
        client.get().setSold(client.get().getSold()+ suma);
        clientRepository.save(client.get());

        return "Depunere reusita";
    }

    public String retragere(String id, double suma){
        Optional<Client> client = Optional.of(clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found") ));

        if(client.get().getSold() < suma){
            throw new RuntimeException("Fonduri insuficiente");
        }
        client.get().setSold(client.get().getSold()-suma);
        clientRepository.save(client.get());

        return "Retragere reusita";
    }


    public String transfer(String id1, String id2, double suma){
        Optional<Client> client1 = Optional.of(clientRepository.findById(id1).orElseThrow(() -> new RuntimeException("Client not found") ));
        Optional<Client> client2 = Optional.of(clientRepository.findById(id2).orElseThrow(() -> new RuntimeException("Client not found") ));

        if(client1.get().getSold() < suma){
            throw new RuntimeException("Fonduri insuficiente");
        }

        client1.get().setSold(client1.get().getSold()-suma);
        clientRepository.save(client1.get());

        client2.get().setSold(client2.get().getSold()+suma);
        clientRepository.save(client2.get());

        return "Transfer reusit";
    }

}
