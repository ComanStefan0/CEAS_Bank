package com.ceasbank.bankbackend.controller;

import com.ceasbank.bankbackend.dto.ClientRequestDTO;
import com.ceasbank.bankbackend.dto.ClientResponseDTO;
import com.ceasbank.bankbackend.persistence.Client;
import com.ceasbank.bankbackend.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST care gestioneaza operatiile legate clienti.
 * <p>
 * Include functionalitati pentru: obtinerea, adaugarea si stergerea unui client.
 * Toate rutele sunt accesibile prin prefixul /users.
 */

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    /**
     * Returneaza informatii despre un client, pe baza ID-ului specificat.
     *
     * @param clientId id-ul clientului
     * @return obiectul {@link ClientResponseDTO} cu datele clientului
     */
    @Operation(
            summary = "Get client by ID",
            description = "Retrieves the client details based on the specified client ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/{clientId}")
    public ClientResponseDTO getClient(@PathVariable Long clientId) {
        return clientService.getClientById(clientId);
    }

    /**
     * Creeaza un nou client si contul bancar asociat acestuia
     *
     * @param clientRequestDTO obiect care contine informatiile necesare pentru inregistrare
     * @return obiectul {@link ClientResponseDTO} cu datele clientului creat
     */

    @Operation(
            summary = "Create a new client and their account",
            description = "Registers a new client and automatically creates an associated bank account with a 0 balance."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client successfully created",
                    content = @Content(schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
    @PostMapping("/signup")
    public ClientResponseDTO addClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        return clientService.saveClient(clientRequestDTO);
    }

    /**
     * Sterge un client si contul asociat acestuia.
     *
     * @param clientId ID-ul clientului de sters.
     */
    @Operation(
            summary = "Delete a client and their account",
            description = "Deletes the client with the specified ID along with their associated account."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client and associated account deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
    }
}