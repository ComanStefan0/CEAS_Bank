package com.ceasbank.bankbackend.controller;

import com.ceasbank.bankbackend.persistence.Client;
import com.ceasbank.bankbackend.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public Client adaugaUtilizator(@RequestBody Client client) {
        return clientService.saveClient(client);
    }
}