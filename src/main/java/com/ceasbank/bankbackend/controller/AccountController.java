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

@RestController
@RequestMapping("/users/{clientId}/account")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;

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

    @Operation(
            summary = "Get account by account ID",
            description = "Fetches the details of an account using its ID."
    )
    @ApiResponse(responseCode = "200", description = "Account retrieved successfully",
            content = @Content(schema = @Schema(implementation = Account.class)))
    @GetMapping("/{accountId}")
    public Account getAccount(@PathVariable Long accountId) {
        return accountService.findByAccountId(accountId);
    }

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
    public Account accountOperation(@PathVariable Long accountId, @RequestBody double amount ) {
        return accountService.accountOperation(accountId, amount);
    }

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
    @PostMapping("/{accountId}/transfer/{destAccId}")
    public Account transferUtilizator(@PathVariable Long accountId, @PathVariable Long destAccId, @RequestBody double amount) {
        return accountService.transfer(accountId, destAccId, amount);
    }
}
