package com.ceasbank.bankbackend.controller;

import com.ceasbank.bankbackend.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;

    @PutMapping("/depunere/{id}") //id = id-ul contului
    public ResponseEntity<String> depunere(@PathVariable Long id, @RequestBody double amount ) {
        accountService.depunere(id, amount);
        return ResponseEntity.ok("Depunere reusita");
    }

    @PutMapping("/retragere/{id}")
    public ResponseEntity<String> retragere(@PathVariable Long id, @RequestBody double amount) {
        accountService.retragere(id, amount);
        return ResponseEntity.ok("Retragere reusita");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferUtilizator(@RequestBody Map<String, String> body) {
        Long id1 = parseLong(body.get("id1"));
        Long id2 = parseLong(body.get("id2"));
        double suma = Double.parseDouble(body.get("suma"));
        accountService.transfer(id1, id2, suma);
        return ResponseEntity.ok("Transfer reusit");
    }
}
