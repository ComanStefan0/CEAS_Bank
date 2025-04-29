package com.ceasbank.bankbackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class ClientController {

    private final ClientRepository clientRepository;
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientRepository clientRepository, ClientService clientService) {
        this.clientRepository = clientRepository;
        this.clientService = clientService;
    }

    @PostMapping("/adauga")
    public String adaugaClient(@RequestBody Client client) {
        clientRepository.save(client);
        return "Client adăugat cu succes!";
    }

    @PutMapping("/depunere/{id}")
    public String depunere(@PathVariable String id, @RequestBody Map<String, Double> body) {
        clientService.depunere(id, body.get("suma"));
        return "Depunere reusita";
    }

    @PutMapping("/retragere/{id}")
    public String retragere(@PathVariable String id, @RequestBody Map<String, Double> body) {
        clientService.retragere(id, body.get("suma"));
        return "Retragere reusita";
    }

    @PostMapping("/transfer")
    public String transferClient(@RequestBody Map<String, String> body) {
        String id1 = body.get("id1");
        String id2 = body.get("id2");
        double suma = Double.parseDouble(body.get("suma"));
        clientService.transfer(id1, id2, suma);
        return "Transfer reusit";
    }

}
