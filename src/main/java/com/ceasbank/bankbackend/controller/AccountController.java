package com.ceasbank.bankbackend.controller;

import com.ceasbank.bankbackend.persistence.Account;
import com.ceasbank.bankbackend.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST care gestioneaza operatiile asupra conturilor bancare.
 * <p>
 * Toate rutele sunt prefixate cu /users/{clientId}/account.
 * Operatiile includ: obtinerea unui cont, actualizarea soldului si transferul de bani
 */
@RestController
@RequestMapping("/users/{clientId}/account")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;

    /**
     * Se obtine contul bancar asociat unui anumit client.
     *
     * @param clientId id-ul clientului
     * @return contul bancar al acestuia
     */
    @Operation(
            summary = "Get account by client ID",
            description = "Fetches the bank account associated with a specific client ID."
    )
    @ApiResponse(responseCode = "200", description = "Account retrieved successfully",
            content = @Content(schema = @Schema(implementation = Account.class)))
    @GetMapping
    public Account getAccountByClientId(@PathVariable Long clientId) {
        return accountService.getByClientId(clientId);
    }

    /**
     * Obtine detalii despre un cont folosind ID-ul contului.
     *
     * @param accountId id-ul contului
     * @return contul gasit
     */
    @Operation(
            summary = "Get account by account ID",
            description = "Fetches the details of an account using its ID."
    )
    @ApiResponse(responseCode = "200", description = "Account retrieved successfully",
            content = @Content(schema = @Schema(implementation = Account.class)))
    @GetMapping("/{accountId}")

    public Account getAccount(@PathVariable("accountId") Long accountId) {
        return accountService.findByAccountId(accountId);
    }

    /**
     * Actulizeaza soldul contului
     *
     * @param accountId id-ul contului
     * @param amount    suma de actualizat (pozitiva pentru depunere, negativa pentru retragere)
     * @return contul actualizat
     */
    @Operation(
            summary = "Perform account operation",
            description = "Updates the account balance by depositing (positive value) or withdrawing (negative value)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful",
                    content = @Content(schema = @Schema(implementation = Account.class))),
            @ApiResponse(responseCode = "400", description = "Insufficient funds or invalid amount",
                    content = @Content)
    })
    @PutMapping("/{accountId}")
    public Account accountOperation(@PathVariable("accountId") Long accountId, @RequestBody double amount) {

        return accountService.accountOperation(accountId, amount);
    }

    /**
     * Se transfera bani de la un cont la altul.
     *
     * @param senderId  id-ul contului expeditor
     * @param destAccId id-ul contului destinatar (prin clientID in URL)
     * @param amount    suma de transferat
     * @return contul expeditor actualizat
     */
    @Operation(
            summary = "Transfer funds",
            description = "Transfers an amount of money from one account to another."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer successful",
                    content = @Content(schema = @Schema(implementation = Account.class))),
            @ApiResponse(responseCode = "400", description = "Insufficient funds or invalid accounts",
                    content = @Content)
    })
    @PostMapping("/{accountId}/transfer")
    public Account transfer(@PathVariable("accountId") Long senderId, @PathVariable("clientId") Long destAccId, @RequestBody double amount) {

        return accountService.transfer(senderId, destAccId, amount);
    }
}
